package demo.springboot.kafka.sources.biz.impl;

import com.google.common.base.Charsets;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import demo.springboot.kafka.sources.basic.domain.MqKafkaData;
import demo.springboot.kafka.sources.basic.domain.MqKafkaSendResult;
import demo.springboot.kafka.sources.biz.IMqKafkaProducer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/7/29 下午11:28
 */
public class MqKafkaProducer<T> implements IMqKafkaProducer<T>, InitializingBean {

    protected static Logger logger = LoggerFactory.getLogger(MqKafkaProducer.class);

    private String env;//环境

    private String topics;

    private List<String> topicsArray;

    private MessageChannel channel;

    private KafkaTemplate<String, MqKafkaData<T>> kafkaTemplate;

    @Override
    public MqKafkaSendResult send(String topic, MqKafkaData value) {
        return send(topic, value, null, null);
    }

    @Override
    public MqKafkaSendResult send(String topic, MqKafkaData value, String key) {
        return send(topic, value, key, null);
    }

    @Override
    public MqKafkaSendResult send(String topic, MqKafkaData value, String key, Integer partitions, String keyWord) {
        if (partitions == null || partitions <= 0 || StringUtils.isBlank(keyWord)) {
            throw new RuntimeException("partitions和keyWord不能为空");
        }

        int hash = murmur3_32hash(keyWord);
        return send(topic, value, key, partitions, getPartitionNo(hash, partitions));
    }

    @Override
    public MqKafkaSendResult send(String topic, MqKafkaData value, String key, Integer partitions, Integer keyWord) {
        if (partitions == null || keyWord == null) {
            throw new RuntimeException("partitions和keyNumber不能为空");
        }
        int partition = 0;
        if (partitions > 1) {
            partition = keyWord % partitions;
        }
        return send(topic, value, key, partition);
    }


    @Override
    public Future<SendResult<String, MqKafkaData>> asyncSend(String topic, MqKafkaData value, String key, Integer partitions, String keyWord) {
        if (partitions == null || partitions <= 0 || StringUtils.isBlank(keyWord)) {
            throw new RuntimeException("partitions和keyWord不能为空");
        }
        int hash = murmur3_32hash(keyWord);
        return asyncSend(topic, value, key, getPartitionNo(hash, partitions));
    }

    @Override
    public MqKafkaSendResult syncSend(String topic, MqKafkaData value) {
        return syncSend(topic, value, null, null);
    }

    @Override
    public MqKafkaSendResult syncSend(String topic, MqKafkaData value, String key) {
        return syncSend(topic, value, key, null);
    }

    @Override
    public MqKafkaSendResult syncSend(String topic, MqKafkaData value, String key, Integer partitions, String keyWord) {
        if (partitions == null || partitions <= 0 || StringUtils.isBlank(keyWord)) {
            throw new RuntimeException("partitions和keyWord不能为空");
        }

        int hash = murmur3_32hash(keyWord);
        return syncSend(topic, value, key, partitions, getPartitionNo(hash, partitions));
    }

    @Override
    public MqKafkaSendResult syncSend(String topic, MqKafkaData value, String key, Integer partitions, Integer keyWord) {
        if (partitions == null || keyWord == null) {
            throw new RuntimeException("partitions和 keyWord 不能为空");
        }
        int partition = keyWord % partitions;
        return syncSend(topic, value, key, partition);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (StringUtils.isBlank(topics) || (channel == null && kafkaTemplate == null)) {
            throw new Exception("topics和channel或kafkaTemplate不能为空");
        }
        try {
            String[] tempArray = topics.split("\\|");
            if (tempArray == null || tempArray.length <= 0) {
                throw new Exception("topics解析失败格式不正确:" + topics);
            }
            topicsArray = new ArrayList<>();
            for (int i = 0; i < tempArray.length; i++) {
                topicsArray.add(tempArray[i]);
            }
            //添加私信topic
            topicsArray.add("kafka_die_topic");
        } catch (Exception e) {
            throw new Exception("topics解析失败" + e.getMessage());
        }
    }


    /**
     * 同步发送
     *
     * @param topic
     * @param value
     * @param partition
     * @return
     */
    @Override
    public MqKafkaSendResult send(String topic, MqKafkaData<T> value, String key, Integer partition) {
        if (kafkaTemplate == null) {
            throw new RuntimeException("同步模式,需要注入kafkaTemplate");
        }
        Message<MqKafkaData<T>> message = buildMessage(topic, value, key, partition);
        try {
            //KafkaTemplate 进行发送
            ListenableFuture<SendResult<String, MqKafkaData<T>>> result = kafkaTemplate.send(message);
            MqKafkaSendResult kafkaSendResult = null;
            //if this task completed.
            //TODO 是不是这里等待...
            if (result.isDone()) {
                kafkaSendResult = new MqKafkaSendResult(result.get().getRecordMetadata().offset(),
                        result.get().getRecordMetadata().partition(), message.getHeaders().getTimestamp(),
                        message.getHeaders().getId().toString());
            } else if (result.isCancelled()) {//if this task was cancelled before it completed
                kafkaSendResult = new MqKafkaSendResult("-001", "未知异常发送失败");
            }//debug 日志开关
            if (logger.isDebugEnabled()) {
                logger.debug(kafkaSendResult.toString() + ",topic=" + topic + ",value=" + value.toString());
            }
            return kafkaSendResult;
        } catch (Exception e) {
            logger.error("", e);
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * 异步发送
     *
     * @param topic
     * @param value
     * @param key
     * @param partition
     * @return
     */
    @Override
    public Future<SendResult<String, MqKafkaData<T>>> asyncSend(String topic, MqKafkaData<T> value, String key, Integer partition) {
        if (kafkaTemplate == null) {
            throw new RuntimeException("同步模式,需要注入kafkaTemplate");
        }
        Message<MqKafkaData<T>> message = buildMessage(topic, value, key, partition);
        return kafkaTemplate.send(message);
    }


    public MqKafkaSendResult syncSend(String topic, MqKafkaData<T> value, String key, Integer partition) {
        if (channel == null) {
            throw new RuntimeException("异步队列模式,需要注入channel");
        }
        try {
            Message<MqKafkaData<T>> message = buildMessage(topic, value, key, partition);
            MqKafkaSendResult kafkaSendResult = new MqKafkaSendResult(message.getHeaders().getTimestamp(),
                    message.getHeaders().getId().toString());

            // MessageChannel 进行发送 TODO
            boolean result = channel.send(message);
            if (!result) {
                kafkaSendResult = new MqKafkaSendResult("-001", "未知异常发送失败");
            }
            if (logger.isDebugEnabled()) {
                logger.debug(kafkaSendResult.toString() + ",topic=" + topic + ",value=" + value.toString());
            }
            return kafkaSendResult;
        } catch (Exception e) {
            logger.error("", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * @param topic
     * @param value
     * @param key
     * @param partition
     * @return
     */
    private Message<MqKafkaData<T>> buildMessage(String topic, MqKafkaData<T> value, String key, Integer partition) {
        if (StringUtils.isBlank(topic) || value == null) {
            throw new IllegalStateException("topic 和 value不能为空,或channel不能为空");
        }
        if (!topicsArray.contains(topic)) {
            logger.error("[" + topic + "]还没有在配置文件中定义");
            throw new RuntimeException("发送Message [" + topic + "]还没有在配置文件中定义");
        }
        value.setCt(System.currentTimeMillis());
        MessageBuilder<MqKafkaData<T>> builder = MessageBuilder.withPayload(value);
        builder.setHeader(KafkaHeaders.TOPIC, topic);
        if (StringUtils.isNotBlank(key)) {
            //设置 kafka_messageKey
            builder.setHeader(KafkaHeaders.MESSAGE_KEY, key);
        }
        if (partition != null) {
            //设置 kafka_partitionId
            builder.setHeader(KafkaHeaders.PARTITION_ID, partition);
        }
        Message<MqKafkaData<T>> messageBuilder = builder.build();
        return messageBuilder;
    }


    /**
     * 生成hash值
     *
     * @param str
     */
    private int murmur3_32hash(String str) {
        HashFunction hf = Hashing.murmur3_32();
        return hf.newHasher().putString(str, Charsets.UTF_8).hash().asInt();
    }


    /**
     * 根据hash值定位分区
     *
     * @param hash            哈希值
     * @param partitionAllNum 分区总数
     * @return 具体分区
     */
    private int getPartitionNo(int hash, int partitionAllNum) {
        /* 负数结果则加上分区总数，得到分区数 */
        int partitionNo = hash % partitionAllNum;
        return partitionNo < 0 ? partitionNo + partitionAllNum : partitionNo;
    }
}
