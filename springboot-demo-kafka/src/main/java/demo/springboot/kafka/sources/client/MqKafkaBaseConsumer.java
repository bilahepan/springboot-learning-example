package demo.springboot.kafka.sources.client;

import com.alibaba.fastjson.JSONObject;
import demo.springboot.kafka.sources.basic.domain.MqKafkaConsumerConfig;
import demo.springboot.kafka.sources.basic.domain.MqKafkaConsumerConfigServer;
import demo.springboot.kafka.sources.basic.domain.MqKafkaData;
import demo.springboot.kafka.sources.biz.IMqKafkaBizConsumer;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/7/29 下午11:36
 */
public abstract class MqKafkaBaseConsumer<T> {

    protected static Logger logger = LoggerFactory.getLogger(MqKafkaBaseConsumer.class);

    private MqKafkaConsumerConfigServer kafkaConsumerConfigServer;


    public MqKafkaConsumerConfigServer getKafkaConsumerConfigServer() {
        return kafkaConsumerConfigServer;
    }

    public void setKafkaConsumerConfigServer(MqKafkaConsumerConfigServer kafkaConsumerConfigServer) {
        this.kafkaConsumerConfigServer = kafkaConsumerConfigServer;
    }

    public MqKafkaConsumerConfig getConsumerConfig(String topic) {
        if (kafkaConsumerConfigServer != null) {
            return kafkaConsumerConfigServer.getConsumerConfig(topic);
        }
        return null;
    }


    /**
     * 根据key获取原生的kafka消费配置值
     *
     * @param key 原生配置key
     */
    public Object getNativeConsumerValue(String key) {
        return Objects.nonNull(kafkaConsumerConfigServer) && StringUtils.isNotEmpty(key) ?
                kafkaConsumerConfigServer.getNativeConsumerConfigValue(key) : null;
    }

    /**
     * 消费消息
     * @param record
     * */
    protected void doMessage(ConsumerRecord<String, MqKafkaData<T>> record) {
        MqKafkaConsumerConfig kafkaConsumerConfig = null;
        //ConsumerRecord 使用 TODO
        if (record == null || record.value() == null) {
            logger.error("[topic:" + record.topic() + "],消息值为null");
            return;
        }
        //rt 表示消息发送使用的时间
        record.value().setRt(System.currentTimeMillis() - record.value().getCt());
        try {//Debug 日志开关
            if (logger.isDebugEnabled()) {
                logger.debug("[" + record.topic() + "],value:" + record.value().toString() + ",offset=" + record.offset()
                        + ",partition=" + record.partition() + ",key=" + record.key() + ",threadId=" + Thread.currentThread().getId());
            }
            kafkaConsumerConfig = getConsumerConfig(record.topic());
            if (kafkaConsumerConfig == null || kafkaConsumerConfig.getConsumer() == null) {
                logger.error("[topic:" + record.topic() + "],还没有配置,无法消费");
                return;
            }
            IMqKafkaBizConsumer consumer = kafkaConsumerConfig.getConsumer();

            MqKafkaData data = record.value();
            //此处 TODO
            Type[] types = consumer.getClass().getGenericInterfaces();
            for (Type type : types) {
                if (type instanceof ParameterizedType) {
                    ParameterizedType pt = (ParameterizedType) type;
                    if (pt.getRawType().equals(IMqKafkaBizConsumer.class)) {
                        if (pt.getActualTypeArguments()[0] instanceof Class) {
                            Class clazz = (Class) pt.getActualTypeArguments()[0];
                            Object o = data.getData();
                            if (o instanceof JSONObject && !(clazz.equals(JSONObject.class))) {
                                JSONObject entity = ((JSONObject) o);
                                entity.remove("@type");
                                Object t = entity.toJavaObject(clazz);
                                data.setData(t);
                            }
                        }
                    }
                }
            }

            boolean t = consumer.consumer(record.topic(), record.value(), record.partition(), record.offset(),
                    record.key());
            if (!t) {//默认不重试
                logger.warn("[topic:" + record.topic() + "],消费失败,但业务允许放弃:" + record.value().toString());
            }
            return;
        } catch (Exception e) {
            logger.error("[topic:" + record.topic() + "],执行跑出异常了:" + record.value().toString() + "kafka_error2:", e);
            //允许放弃，则不重试
            if (kafkaConsumerConfig.getIgnore()) {
                return;
            } else {//抛异常
                throw new RuntimeException(
                        "-1,[topic:" + record.topic() + "],异常重试:" + record.value().toString() + "kafka_error1:", e);
            }
        }
    }

}
