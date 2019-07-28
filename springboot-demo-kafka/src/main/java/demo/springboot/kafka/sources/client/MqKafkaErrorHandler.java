package demo.springboot.kafka.sources.client;

import demo.springboot.kafka.sources.basic.domain.MqKafkaConsumerConfig;
import demo.springboot.kafka.sources.basic.domain.MqKafkaConsumerConfigServer;
import demo.springboot.kafka.sources.basic.domain.MqKafkaData;
import demo.springboot.kafka.sources.biz.IMqKafkaProducer;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.ErrorHandler;

import java.util.Objects;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/7/25 下午9:46
 */
public class MqKafkaErrorHandler implements ErrorHandler {

    protected static Logger logger = LoggerFactory.getLogger(MqKafkaErrorHandler.class);

    private String errorTopic;

    private String dieTopic = "kafka_die_topic";//默认的死信队列

    private IMqKafkaProducer mqKafkaProducer;

    private MqKafkaConsumerConfigServer kafkaConsumerConfigServer;

    /**
     * kafka消费组
     */
    private static String GROUP_ID = "group.id";

    public void setErrorTopic(String errorTopic) {
        this.errorTopic = errorTopic;
    }

    public void setDieTopic(String dieTopic) {
        this.dieTopic = dieTopic;
    }

    public void setMqKafkaProducer(IMqKafkaProducer mqKafkaProducer) {
        this.mqKafkaProducer = mqKafkaProducer;
    }

    public void setKafkaConsumerConfigServer(MqKafkaConsumerConfigServer kafkaConsumerConfigServer) {
        this.kafkaConsumerConfigServer = kafkaConsumerConfigServer;
    }

    @Override
    public void handle(Exception thrownException, ConsumerRecord<?, ?> consumerRecord) {
        if (consumerRecord == null) {
            logger.error("consumerRecord is null", thrownException);
            return;
        }

        if (mqKafkaProducer == null || kafkaConsumerConfigServer == null || StringUtils.isBlank(dieTopic)) {
            logger.error("配置缺失,请检查");
            return;
        }
        if (!(consumerRecord.topic() instanceof java.lang.String) || !(consumerRecord.value() instanceof MqKafkaData)) {
            logger.error("消息内容不合法" + consumerRecord.topic() + "," + consumerRecord.value());
            return;
        }
        MqKafkaConsumerConfig kafkaConsumerConfig = kafkaConsumerConfigServer.getConsumerConfig(consumerRecord.topic());
        if (kafkaConsumerConfig == null) {
            logger.error("找不到该topic的配置,请检查" + consumerRecord.topic());
            return;
        }
        MqKafkaData data = (MqKafkaData) consumerRecord.value();
        data.setCount(data.getCount() + 1);
        data.addOffsets(consumerRecord.offset());
        Object groupId = kafkaConsumerConfigServer.getNativeConsumerConfigValue(GROUP_ID);
        data.setGroupId(Objects.nonNull(groupId) ? String.valueOf(groupId) : null);
        if (data.getCount() >= kafkaConsumerConfig.getTryNum()) {
            mqKafkaProducer.send(dieTopic, data);
            logger.error("消息消费失败,转入死信队列:topic=" + consumerRecord.topic() + "," + data.toString() + ","
                    + thrownException.toString());
        } else {
            String tryTopic = consumerRecord.topic();
            //TODO 这个逻辑可以删掉哦，感觉
            if (StringUtils.isNotBlank(errorTopic)) {
                tryTopic = errorTopic;
            }
            String key = Objects.isNull(consumerRecord.key()) ? null : String.valueOf(consumerRecord.key());
            mqKafkaProducer.send(consumerRecord.topic(), data, key, consumerRecord.partition());
            logger.warn("消息消费失败,进行重试消费" + tryTopic + "," + data.toString() + "," + thrownException.toString());
        }
    }
}