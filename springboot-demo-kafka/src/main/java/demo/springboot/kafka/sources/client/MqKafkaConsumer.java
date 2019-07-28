//package demo.springboot.kafka.sources.client;
//
//import demo.springboot.kafka.sources.basic.MqKafkaConsumerConfig;
//import demo.springboot.kafka.sources.biz.IMqKafkaConsumerListener;
//import demo.springboot.kafka.sources.biz.impl.MqKafkaMessageListenerContainer;
//import org.apache.commons.collections4.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.DisposableBean;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.listener.AbstractMessageListenerContainer;
//import org.springframework.kafka.listener.ContainerProperties;
//import org.springframework.kafka.support.TopicPartitionInitialOffset;
//
//import java.nio.charset.Charset;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
///**
// * @author: 文若[gaotc@tuya.com]
// * @date: 2019/7/25 下午9:09
// */
//public class MqKafkaConsumer<T> implements InitializingBean, DisposableBean {
//
//    protected static Logger logger = LoggerFactory.getLogger(MqKafkaConsumer.class);
//
//    private List<MqKafkaConsumerConfig> consumerConfigs;
//
//    private DefaultKafkaConsumerFactory consumerFactory;
//
//    private IMqKafkaConsumerListener consumerListener;
//
//    private MqKafkaErrorHandler kafkaErrorHandler;
//
//
//    private long ackTime = 6000;
//
//    private List<MqKafkaMessageListenerContainer> listenerContainerList = new ArrayList<>();
//
//    @Value("${kafka.burrow.addappinfo.url:}")
//    private String burrowApiUrl;
//
//    @Value("${runtime.env:daily}")
//    private String runtimeEnv;
//
//    /**
//     * @see org.springframework.kafka.core.DefaultKafkaConsumerFactory#configs
//     */
//    private final static String CONSUMER_FACTORY_CONFIGS_FIELD = "configs";
//
//    public void setConsumerConfigs(List<MqKafkaConsumerConfig> consumerConfigs) {
//        this.consumerConfigs = consumerConfigs;
//    }
//
//    public List<MqKafkaConsumerConfig> getConsumerConfigs() {
//        return consumerConfigs;
//    }
//
//    public void setConsumerFactory(DefaultKafkaConsumerFactory consumerFactory) {
//        this.consumerFactory = consumerFactory;
//    }
//
//    /**
//     * 当前kafka 0.10.0.1版本，不支持在相同group下来回进行切换，
//     * 否则进行offset commit是报异常，针对该问题使用新group方式进行切换。
//     * 使用不同group切换消费，必须保证offset具有连续性，需要手动指定offset，需要创建{@link KafkaConsumer}
//     *
//     * @return
//     */
//    @Deprecated
//    public DefaultKafkaConsumerFactory getConsumerFactory() {
//        return consumerFactory;
//    }
//
//    public void setConsumerListener(IKafkaConsumer consumerListener) {
//        this.consumerListener = consumerListener;
//    }
//
//    public void setKafkaErrorHandler(KafkaErrorHandler kafkaErrorHandler) {
//        this.kafkaErrorHandler = kafkaErrorHandler;
//    }
//
//    public void setSwitchConsumerMode(SwitchConsumerMode switchConsumerMode) {
//        this.switchConsumerMode = switchConsumerMode;
//    }
//
//    public void setAckTime(Long time) {
//        if (time == null || time <= 1000L) {
//            throw new RuntimeException("ackTime必须大于1000ms");
//        }
//        this.ackTime = time;
//    }
//
//    /**
//     * 配置上下文环境
//     * <p>建议在{@link DefaultKafkaConsumerFactory#configs}配置<code>enable.auto.commit</code>为<code>false</code>，由spring封装逻辑来控制提交offset
//     *
//     * @throws Exception
//     */
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        //避免和burrow的通信对系统产生影响
//        new Thread(() -> Optional.ofNullable(createRequest()).
//                ifPresent(request -> sendRequest(request))).start();
//        AbstractMessageListenerContainer.AckMode ackMode = AbstractMessageListenerContainer.AckMode.TIME;
//        if (consumerListener.getClass().getName().equals("com.tuya.basic.mq.impl.KafkaAcknowledgingConsumer")) {
//            ackMode = AbstractMessageListenerContainer.AckMode.MANUAL_IMMEDIATE;
//        }
//        if (consumerConfigs == null || consumerConfigs.size() <= 0 || consumerFactory == null
//                || consumerListener == null) {
//            throw new Exception("kafka消费者配置出错");
//        }
//
//        KafkaConsumerLifeCycleController.addSingleConsumerContext(this);
//        KafkaLifeCycleController.addSingleConsumerContext(this);
//
//        Map<String, Object> nativeConfigs = KafkaUtil.getNativeConsumerConfigs(consumerFactory, CONSUMER_FACTORY_CONFIGS_FIELD);
//        KafkaConsumerConfigServer kafkaConsumerConfigServer = new KafkaConsumerConfigServer(consumerConfigs, nativeConfigs);
//        kafkaErrorHandler.setKafkaConsumerConfigServer(kafkaConsumerConfigServer);
//        consumerListener.setKafkaConsumerConfigServer(kafkaConsumerConfigServer);
//
//        for (int i = 0; i < consumerConfigs.size(); i++) {
//            List<TopicPartitionInitialOffset> partitionses = new ArrayList<>();
//            int concurrency = consumerConfigs.get(i).getWorks();
//            String topic = consumerConfigs.get(i).getTopic();
//            ContainerProperties containerProperties;
//            if (CollectionUtils.isNotEmpty(consumerConfigs.get(i).getPartitions())) {
//                for (KafkaPartitions partitions : consumerConfigs.get(i).getPartitions()) {
//                    if (partitions.getOffset() != null) {
//                        partitionses.add(new TopicPartitionInitialOffset(consumerConfigs.get(i).getTopic(),
//                                partitions.getPartition(), partitions.getOffset()));
//                    } else {
//                        partitionses.add(new TopicPartitionInitialOffset(consumerConfigs.get(i).getTopic(),
//                                partitions.getPartition()));
//                    }
//                }
//                concurrency = partitionses.size();
//                TopicPartitionInitialOffset[] partitionInitialOffsets = partitionses
//                        .toArray(new TopicPartitionInitialOffset[partitionses.size()]);
//                containerProperties = new ContainerProperties(partitionInitialOffsets);
//            } else {
//                containerProperties = new ContainerProperties(topic);
//            }
//
//            containerProperties.setMessageListener(consumerListener);
//            containerProperties.setAckMode(ackMode);
//            containerProperties.setAckTime(ackTime);
//            containerProperties.setErrorHandler(kafkaErrorHandler);
//
//            MqKafkaMessageListenerContainer kafkaMessageListenerContainer = new MqKafkaMessageListenerContainer(
//                    consumerFactory, containerProperties);
//            kafkaMessageListenerContainer.setConcurrency(concurrency);
//            kafkaMessageListenerContainer.setBeanName(consumerConfigs.get(i).getBeanName());
//            listenerContainerList.add(kafkaMessageListenerContainer);
//        }
//
//        if (null == switchConsumerMode) {
//            logger.info("未配置切换模式，继续auto rebalance模式消费。config={}", consumerConfigs);
//            start();
//        } else {
//            switchConsumerMode.setOldKafkaConsumerContext(this);
//            if (!switchConsumerMode.runningWithManualRebalance()) {
//                logger.info("切换模式非assign partition模式，继续auto rebalance模式消费。config={}", consumerConfigs);
//                start();
//            }
//        }
//    }
//
//    /**
//     * 在应用停止前停止消费，做到优雅消费
//     * <p>必须在{@link DefaultKafkaConsumerFactory#configs}配置<code>enable.auto.commit</code>为<code>false</code>，
//     * 因为这里默认使用spring封装的提交offset逻辑，在Rebalance发生前提交offset：
//     * {@link KafkaMessageListenerContainer.ListenerConsumer#createRebalanceListener(Consumer)}实现了
//     * {@link ConsumerRebalanceListener#onPartitionsRevoked(Collection)}
//     *
//     * @throws Exception
//     * @see KafkaMessageListenerContainer.ListenerConsumer#createRebalanceListener(org.apache.kafka.clients.consumer.Consumer)
//     */
//    @Override
//    public void destroy() throws Exception {
//        stop();
//    }
//
//    /**
//     * 勿用，会引起线上故障，随时会删掉
//     */
//    @Deprecated
//    public void stop() {
//        logger.info("开始执行 stop kafka container");
//        if (CollectionUtils.isNotEmpty(listenerContainerList)) {
//            for (MqKafkaMessageListenerContainer listenerContainer : listenerContainerList) {
//                listenerContainer.doStop();
//            }
//        }
//    }
//
//    /**
//     * 勿用，会引起线上故障，随时会删掉
//     */
//    @Deprecated
//    public void start() {
//        logger.info("开始执行 restart kafka container");
//        if (CollectionUtils.isEmpty(listenerContainerList)) {
//            return;
//        }
//        for (MqKafkaMessageListenerContainer listenerContainer : listenerContainerList) {
//            if (listenerContainer.isRunning()) {
//                logger.info("kafka已经启动在运行，不能重复start");
//                continue;
//            }
//            listenerContainer.doStart();
//        }
//    }
//
//    /**
//     * @Author: Lixy@tuya.com
//     * @Date: 2019-04-03 10:28
//     * <p>
//     * 在spring项目启动后，获取该应用配置了kafka的相关信息，主要是记录
//     * 应用名 kafka的消费组名 处理的topic 等信息
//     * 并传入公司内部kafka监控系统
//     */
//    private BurrowRequest createRequest() {
////	    if(!"daily".equals(runtimeEnv)){
////	        logger.info("only send burrow info in daily env now {}",runtimeEnv);
////	        return null;
////        }
//        if (StringUtils.isBlank(burrowApiUrl)) {
//            logger.info("could not get burrow api url {}", burrowApiUrl);
//            return null;
//        }
//
//        //TODO lixy 消费组的信息好像木有暴露出来
//
//        String group = "unknown";
//        try {
//            Field f;
//            f = this.getConsumerFactory().getClass().getDeclaredField("configs");
//            f.setAccessible(true);
//            Map m = (Map) f.get(this.getConsumerFactory());
//            group = (String) m.get("group.id");
//        } catch (NoSuchFieldException e) {
////			e.printStackTrace();
//            logger.warn(e.getMessage());
//        } catch (IllegalAccessException e) {
//            logger.warn(e.getMessage());
//        }
//
//        BurrowRequest request = new BurrowRequest();
//        request.setGroup(group);
//        request.setAppName(System.getProperty("project.name", "unknown"));
////            request.setGroup(v.getConsumerFactory().);
//        List<KafkaConsumerConfig> l = this.getConsumerConfigs();
//        request.setTopics(l.stream().map(conf -> conf.getTopic()).collect(Collectors.toList()));
//        return request;
//    }
//
//    /*
//     * 发送给我的burrow监控系统，丢到别的线程中 确保不会影响java项目的正常启动
//     */
//    public void sendRequest(BurrowRequest request) {
//        try {
//            String body = JSON.toJSONString(request);
//            logger.info("send burrow request {} {}", burrowApiUrl, body);
//            Map<String, String> result = TuyaHttpClient.getInstance().doPostV2(burrowApiUrl,
//                    null, body, null, Charset.forName("UTF-8"), null);
////        System.out.println(result);
//        } catch (Exception e) {
//            logger.warn(e.getMessage());
//        }
//    }
//
//    public String getBurrowApiUrl() {
//        return burrowApiUrl;
//    }
//
//    public void setBurrowApiUrl(String burrowApiUrl) {
//        this.burrowApiUrl = burrowApiUrl;
//    }
//
//    public String getRuntimeEnv() {
//        return runtimeEnv;
//    }
//
//    public void setRuntimeEnv(String runtimeEnv) {
//        this.runtimeEnv = runtimeEnv;
//    }
//
//    private class BurrowRequest {
//        private String appName;
//        private String group;
//        private List<String> topics;
//
//        public String getAppName() {
//            return appName;
//        }
//
//        public void setAppName(String appName) {
//            this.appName = appName;
//        }
//
//        public String getGroup() {
//            return group;
//        }
//
//        public void setGroup(String group) {
//            this.group = group;
//        }
//
//        public List<String> getTopics() {
//            return topics;
//        }
//
//        public void setTopics(List<String> topics) {
//            this.topics = topics;
//        }
//    }
//}
