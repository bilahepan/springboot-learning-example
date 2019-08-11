package demo.springboot.kafka.sources.basic.domain;

import demo.springboot.kafka.sources.biz.IMqKafkaBatchConsumerConfigListener;
import demo.springboot.kafka.sources.biz.IMqKafkaBatchConsumerErrorHandler;
import demo.springboot.kafka.sources.biz.utils.KafkaUtil;
import demo.springboot.kafka.sources.client.KafkaLifeCycleController;
import demo.springboot.kafka.sources.client.MqKafkaFilterBatchConsumerListener;
import demo.springboot.kafka.sources.client.MqKafkaMessageListenerContainer;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.kafka.support.TopicPartitionInitialOffset;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * <p>kafka批量消费上下文
 *
 * <p>默认的Commit offset方式为{@link org.springframework.kafka.listener.AbstractMessageListenerContainer.AckMode#TIME}，每隔{@link MqKafkaBatchConsumerContext#ackTime}提交一次。
 * 其他Commit offset方式，如同步或异步提交，请在{@link DefaultKafkaConsumerFactory}中配置，该配置会优先级更高。
 * 注意：如果异步提交需要自定callBack，请联系维护同学增加功能
 *
 * <p>如果多个业务逻辑消费{@link IMqKafkaBatchBizConsumer}实现类具有相同配置，可以一起配置<code>consumerConfigs</code>列表；
 * 否则请开启新的<code>KafkaBatchConsumerContext</code>配置。
 */
public class MqKafkaBatchConsumerContext implements InitializingBean, DisposableBean {

    private static Logger logger = LoggerFactory.getLogger(MqKafkaBatchConsumerContext.class);

    /**
     * 针对topic的消费配置，主要关联自定义的消费实现类 {@link demo.springboot.kafka.sources.biz.IMqKafkaBizConsumer}
     * 此外提供线程并发数，简单的重试次数配置
     */
    private List<MqKafkaConsumerConfig> consumerConfigs;

    /**
     * Kafka的原始配置参数，由spring提供封装
     */
    private DefaultKafkaConsumerFactory consumerFactory;

    /**
     * 带配置信息的消息监听器，批量消费时传入
     */
    private IMqKafkaBatchConsumerConfigListener consumerConfigListener;

    /**
     * Kafka带过滤的消息监听器，批量消费时请传入，优先级比{@link IMqKafkaBatchConsumerConfigListener}
     */
    private MqKafkaFilterBatchConsumerListener filterBatchConsumerListener;

    /**
     * 批量消费错误处理器
     */
    private IMqKafkaBatchConsumerErrorHandler batchConsumerErrorHandler;

    /**
     * 监听容器列表
     * 和 {@link MqKafkaBatchConsumerContext#consumerConfigs} 对应
     */
    private List<MqKafkaMessageListenerContainer> listenerContainerList = new ArrayList<>();

    private long ackTime = 6000L;//ack超时

    /**  */
    private AbstractMessageListenerContainer.AckMode ackMode = AbstractMessageListenerContainer.AckMode.TIME;

    /**
     * @see org.springframework.kafka.core.DefaultKafkaConsumerFactory#configs
     */
    private final static String CONSUMER_FACTORY_CONFIGS_FIELD = "configs";

    @Override
    public void afterPropertiesSet() throws Exception {
        if (CollectionUtils.isEmpty(consumerConfigs) || Objects.isNull(consumerFactory) || (Objects.isNull(consumerConfigListener) && Objects.isNull(filterBatchConsumerListener))) {
            throw new IllegalArgumentException("kafka消费者配置出错. 请检查配置consumerConfigs、consumerFactory、" +
                    "filterBatchConsumerListener、consumerConfigListener.");
        }

        KafkaLifeCycleController.addBatchConsumerContext(this);

        //初始化上下文中的属性
        List<MqKafkaConsumerConfig> kafkaConsumerConfigs = Collections.unmodifiableList(consumerConfigs);
        Map<String, Object> nativeConfigs = KafkaUtil.getNativeConsumerConfigs(consumerFactory, CONSUMER_FACTORY_CONFIGS_FIELD);
        MqKafkaConsumerConfigServer kafkaConsumerConfigServer = new MqKafkaConsumerConfigServer(kafkaConsumerConfigs, nativeConfigs);
        batchConsumerErrorHandler.setConfigServer(kafkaConsumerConfigServer);

        if (Objects.nonNull(consumerConfigListener)) {
            consumerConfigListener.setConfigServer(kafkaConsumerConfigServer);
        }

        if (Objects.nonNull(filterBatchConsumerListener)) {
            Assert.notNull(filterBatchConsumerListener.getConsumerConfigListener(), "配置过滤消息监听器时，消费监听器不能为null.");
            Assert.notNull(filterBatchConsumerListener.getRecordFilterStrategy(), "配置过滤消息监听器时，过滤器不能为null.");
            //
            filterBatchConsumerListener.getConsumerConfigListener().setConfigServer(kafkaConsumerConfigServer);
            filterBatchConsumerListener.getRecordFilterStrategy().setConfigServer(kafkaConsumerConfigServer);
        }

        //
        for (int i = 0; i < kafkaConsumerConfigs.size(); i++) {
            int concurrency = kafkaConsumerConfigs.get(i).getWorks();//执行线程数
            ContainerProperties containerProperties;
            //不为空
            if (!CollectionUtils.isEmpty(kafkaConsumerConfigs.get(i).getPartitions())) {
                List<TopicPartitionInitialOffset> partitionses = new ArrayList<>();
                for (MqKafkaPartitions partitions : kafkaConsumerConfigs.get(i).getPartitions()) {
                    //TODO
                    if (partitions.getOffset() != null) {
                        partitionses.add(new TopicPartitionInitialOffset(kafkaConsumerConfigs.get(i).getTopic(), partitions.getPartition(), partitions.getOffset()));
                    } else {
                        partitionses.add(new TopicPartitionInitialOffset(kafkaConsumerConfigs.get(i).getTopic(), partitions.getPartition()));
                    }
                }

                concurrency = partitionses.size();
                TopicPartitionInitialOffset[] partitionInitialOffsets = partitionses.toArray(new TopicPartitionInitialOffset[partitionses.size()]);
                //
                containerProperties = new ContainerProperties(partitionInitialOffsets);
            } else {
                containerProperties = new ContainerProperties(kafkaConsumerConfigs.get(i).getTopic());
            }

            //带过滤的消费优先级高
            containerProperties.setMessageListener(Objects.nonNull(filterBatchConsumerListener) ? filterBatchConsumerListener : consumerConfigListener);
            containerProperties.setAckMode(ackMode);
            containerProperties.setAckTime(ackTime);
            containerProperties.setGenericErrorHandler(batchConsumerErrorHandler);
            //
            MqKafkaMessageListenerContainer kafkaMessageListenerContainer = new MqKafkaMessageListenerContainer(consumerFactory, containerProperties);
            kafkaMessageListenerContainer.setConcurrency(concurrency);
            kafkaMessageListenerContainer.doStart();
            //
            listenerContainerList.add(kafkaMessageListenerContainer);
        }

    }


    /**
     * 在应用停止前停止消费，做到优雅消费
     * <p>必须在{@link DefaultKafkaConsumerFactory#configs}配置<code>enable.auto.commit</code>为<code>false</code>，
     * 因为这里默认使用spring封装的提交offset逻辑，在 Rebalance 发生前提交offset：
     * {@link KafkaMessageListenerContainer.ListenerConsumer#createRebalanceListener(Consumer)}实现了
     * {@link ConsumerRebalanceListener#onPartitionsRevoked(Collection)}
     *
     * @throws Exception
     * @see KafkaMessageListenerContainer.ListenerConsumer#createRebalanceListener(org.apache.kafka.clients.consumer.Consumer)
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
        logger.info("开始执行 stop kafka container.");
        //不为空
        if (!CollectionUtils.isEmpty(listenerContainerList)) {
            for (MqKafkaMessageListenerContainer listenerContainer : listenerContainerList) {
                listenerContainer.doStop();
            }
        }
    }

    //-------------------------------------//
    public void setConsumerConfigs(List<MqKafkaConsumerConfig> consumerConfigs) {
        this.consumerConfigs = consumerConfigs;
    }

    public void setConsumerFactory(DefaultKafkaConsumerFactory consumerFactory) {
        this.consumerFactory = consumerFactory;
    }

    public void setConsumerConfigListener(IMqKafkaBatchConsumerConfigListener consumerConfigListener) {
        this.consumerConfigListener = consumerConfigListener;
    }

    public void setFilterBatchConsumerListener(MqKafkaFilterBatchConsumerListener filterBatchConsumerListener) {
        this.filterBatchConsumerListener = filterBatchConsumerListener;
    }

    public void setBatchConsumerErrorHandler(IMqKafkaBatchConsumerErrorHandler batchConsumerErrorHandler) {
        this.batchConsumerErrorHandler = batchConsumerErrorHandler;
    }

    public void setListenerContainerList(List<MqKafkaMessageListenerContainer> listenerContainerList) {
        this.listenerContainerList = listenerContainerList;
    }

    public void setAckTime(long ackTime) {
        this.ackTime = ackTime;
    }

    public void setAckMode(AbstractMessageListenerContainer.AckMode ackMode) {
        this.ackMode = ackMode;
    }
}