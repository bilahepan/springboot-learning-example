package demo.springboot.kafka.sources.client;

import com.alibaba.fastjson.JSON;
import demo.springboot.kafka.sources.basic.domain.MqKafkaConsumerConfig;
import demo.springboot.kafka.sources.basic.domain.MqKafkaConsumerConfigServer;
import demo.springboot.kafka.sources.basic.domain.MqKafkaPartitions;
import demo.springboot.kafka.sources.biz.IMqKafkaConsumer;
import demo.springboot.kafka.sources.biz.utils.KafkaUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.kafka.support.TopicPartitionInitialOffset;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/7/31 下午1:10
 */
public class MqKafkaConsumer<T> implements InitializingBean, DisposableBean {

    protected static Logger logger = LoggerFactory.getLogger(MqKafkaConsumer.class);

    private List<MqKafkaConsumerConfig> consumerConfigs;

    private DefaultKafkaConsumerFactory consumerFactory;

    private IMqKafkaConsumer consumerListener;

    private MqKafkaErrorHandler kafkaErrorHandler;

    private long ackTime = 6000;

    private List<MqKafkaMessageListenerContainer> listenerContainerList = new ArrayList<>();

    @Value("${runtime.env:daily}")
    private String runtimeEnv;

    @Value("${kafka.burrow.url:}")
    private String burrowApiUrl;

    /**
     * @see org.springframework.kafka.core.DefaultKafkaConsumerFactory#configs
     */
    private final static String CONSUMER_FACTORY_CONFIGS_FIELD = "configs";


    //ack 超时必须>=1000ms
    public void setAckTime(Long time) {
        if (time == null || time <= 1000L) {
            throw new RuntimeException("ackTime必须大于1000ms");
        }
        this.ackTime = time;
    }

    /**
     * 配置上下文环境
     * <p>建议在{@link DefaultKafkaConsumerFactory#configs}配置<code>enable.auto.commit</code>为<code>false</code>，由spring封装逻辑来控制提交offset
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //避免和burrow的通信对系统产生影响
        new Thread(() -> Optional.ofNullable(createRequest()).
                ifPresent(request -> sendRequest(request))).start();
        AbstractMessageListenerContainer.AckMode ackMode = AbstractMessageListenerContainer.AckMode.TIME;
        if (consumerListener.getClass().getName().equals("com.tuya.basic.mq.impl.KafkaAcknowledgingConsumer")) {
            ackMode = AbstractMessageListenerContainer.AckMode.MANUAL_IMMEDIATE;
        }
        if (consumerConfigs == null || consumerConfigs.size() <= 0 || consumerFactory == null
                || consumerListener == null) {
            throw new Exception("kafka消费者配置出错");
        }

        KafkaLifeCycleController.addSingleConsumerContext(this);

        Map<String, Object> nativeConfigs = KafkaUtil.getNativeConsumerConfigs(consumerFactory, CONSUMER_FACTORY_CONFIGS_FIELD);
        MqKafkaConsumerConfigServer kafkaConsumerConfigServer = new MqKafkaConsumerConfigServer(consumerConfigs, nativeConfigs);
        kafkaErrorHandler.setKafkaConsumerConfigServer(kafkaConsumerConfigServer);
        consumerListener.setKafkaConsumerConfigServer(kafkaConsumerConfigServer);
        //
        for (int i = 0; i < consumerConfigs.size(); i++) {
            List<TopicPartitionInitialOffset> topicPartitionInitialOffsets = new ArrayList<>();
            int concurrency = consumerConfigs.get(i).getWorks();//执行线程数
            String topic = consumerConfigs.get(i).getTopic();//topic
            ContainerProperties containerProperties;
            if (CollectionUtils.isNotEmpty(consumerConfigs.get(i).getPartitions())) {
                for (MqKafkaPartitions partitions : consumerConfigs.get(i).getPartitions()) {
                    if (partitions.getOffset() != null) {
                        topicPartitionInitialOffsets.add(new TopicPartitionInitialOffset(consumerConfigs.get(i).getTopic(),
                                partitions.getPartition(), partitions.getOffset()));
                    } else {
                        topicPartitionInitialOffsets.add(new TopicPartitionInitialOffset(consumerConfigs.get(i).getTopic(),
                                partitions.getPartition()));
                    }
                }
                concurrency = topicPartitionInitialOffsets.size();
                TopicPartitionInitialOffset[] partitionInitialOffsets = topicPartitionInitialOffsets
                        .toArray(new TopicPartitionInitialOffset[topicPartitionInitialOffsets.size()]);
                containerProperties = new ContainerProperties(partitionInitialOffsets);
            } else {
                containerProperties = new ContainerProperties(topic);
            }

            containerProperties.setMessageListener(consumerListener);
            containerProperties.setAckMode(ackMode);
            containerProperties.setAckTime(ackTime);
            containerProperties.setErrorHandler(kafkaErrorHandler);

            MqKafkaMessageListenerContainer kafkaMessageListenerContainer = new MqKafkaMessageListenerContainer(
                    consumerFactory, containerProperties);
            kafkaMessageListenerContainer.setConcurrency(concurrency);
            kafkaMessageListenerContainer.setBeanName(consumerConfigs.get(i).getBeanName());
            listenerContainerList.add(kafkaMessageListenerContainer);
        }
    }

    /**
     * 在应用停止前停止消费，做到优雅消费
     * <p>必须在{@link DefaultKafkaConsumerFactory#configs}配置<code>enable.auto.commit</code>为<code>false</code>，
     * 因为这里默认使用spring封装的提交offset逻辑，在Rebalance发生前提交offset：
     * {@link MqKafkaMessageListenerContainer.ListenerConsumer#createRebalanceListener(Consumer)}实现了
     * {@link ConsumerRebalanceListener#onPartitionsRevoked(Collection)}
     *
     * @throws Exception
     * @see MqKafkaMessageListenerContainer.ListenerConsumer#createRebalanceListener(org.apache.kafka.clients.consumer.Consumer)
     */
    @Override
    public void destroy() throws Exception {
        stop();
    }

    /**
     * 勿用，会引起线上故障，随时会删掉
     */
    @Deprecated
    public void stop() {
        logger.info("开始执行 stop kafka container");
        if (CollectionUtils.isNotEmpty(listenerContainerList)) {
            for (MqKafkaMessageListenerContainer listenerContainer : listenerContainerList) {
                listenerContainer.doStop();
            }
        }
    }

    /**
     * 勿用，会引起线上故障，随时会删掉
     */
    @Deprecated
    public void start() {
        logger.info("开始执行 restart kafka container");
        if (CollectionUtils.isEmpty(listenerContainerList)) {
            return;
        }
        for (MqKafkaMessageListenerContainer listenerContainer : listenerContainerList) {
            if (listenerContainer.isRunning()) {
                logger.info("kafka已经启动在运行，不能重复start");
                continue;
            }
            listenerContainer.doStart();
        }
    }

    //发送给我的burrow监控系统，丢到别的线程中 确保不会影响java项目的正常启动
    public void sendRequest(BurrowRequest request) {
        try {
            String body = JSON.toJSONString(request);
            logger.info("send burrow request {} {}", burrowApiUrl, body);
            //这里是一个发送请求 TODO
            //Map<String, String> result = TuyaHttpClient.getInstance().doPostV2(burrowApiUrl, null, body, null, Charset.forName("UTF-8"), null);
            //System.out.println(result);
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
    }


    /**
     * 在spring项目启动后，获取该应用配置了kafka的相关信息，主要是记录
     * 应用名 kafka的消费组名 处理的topic 等信息
     * 可以把这些信息收集到，kafka监控系统(需要自研)
     */
    private BurrowRequest createRequest() {
        if (StringUtils.isBlank(burrowApiUrl)) {
            logger.info("could not get burrow api url {}", burrowApiUrl);
            return null;
        }
        //消费组的信息还没有暴露出来  TODO
        String group = "unknown";
        try {
            Field f;
            f = this.getConsumerFactory().getClass().getDeclaredField("configs");
            //在java的反射使用中,如果字段是私有的,那么必须要对这个字段设置
            f.setAccessible(true);
            Map m = (Map) f.get(this.getConsumerFactory());
            group = (String) m.get("group.id");
        } catch (NoSuchFieldException e) {
            //e.printStackTrace();
            logger.warn(e.getMessage());
        } catch (IllegalAccessException e) {
            logger.warn(e.getMessage());
        }

        BurrowRequest request = new BurrowRequest();
        request.setGroup(group);
        request.setAppName(System.getProperty("project.name", "unknown"));
        List<MqKafkaConsumerConfig> l = this.getConsumerConfigs();
        request.setTopics(l.stream().map(conf -> conf.getTopic()).collect(Collectors.toList()));
        return request;
    }

    public String getBurrowApiUrl() {
        return burrowApiUrl;
    }

    public void setBurrowApiUrl(String burrowApiUrl) {
        this.burrowApiUrl = burrowApiUrl;
    }

    public String getRuntimeEnv() {
        return runtimeEnv;
    }

    public void setRuntimeEnv(String runtimeEnv) {
        this.runtimeEnv = runtimeEnv;
    }

    public DefaultKafkaConsumerFactory getConsumerFactory() {
        return consumerFactory;
    }

    public void setConsumerConfigs(List<MqKafkaConsumerConfig> consumerConfigs) {
        this.consumerConfigs = consumerConfigs;
    }

    public List<MqKafkaConsumerConfig> getConsumerConfigs() {
        return consumerConfigs;
    }

    public void setConsumerFactory(DefaultKafkaConsumerFactory consumerFactory) {
        this.consumerFactory = consumerFactory;
    }

    public void setConsumerListener(IMqKafkaConsumer consumerListener) {
        this.consumerListener = consumerListener;
    }

    public void setKafkaErrorHandler(MqKafkaErrorHandler kafkaErrorHandler) {
        this.kafkaErrorHandler = kafkaErrorHandler;
    }

    /**
     * 监控对象体
     *
     * @author: 文若[gaotc@tuya.com]
     * @date: 2019/8/1 上午7:38
     */
    private class BurrowRequest {
        private String appName;
        private String group;
        private List<String> topics;

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public List<String> getTopics() {
            return topics;
        }

        public void setTopics(List<String> topics) {
            this.topics = topics;
        }
    }
}