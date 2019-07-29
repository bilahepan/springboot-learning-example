package demo.springboot.kafka.sources.client;

import com.alibaba.fastjson.JSON;
import demo.springboot.kafka.sources.basic.domain.MqKafkaData;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/7/29 下午11:19
 */
public class MqKafkaJSONDeserializer<T> implements Deserializer<MqKafkaData<T>> {

    protected static Logger logger = LoggerFactory.getLogger(MqKafkaJSONDeserializer.class);

    @Override
    public void configure(Map<String, ?> configs, boolean b) {

    }

    @Override
    public MqKafkaData<T> deserialize(String topic, byte[] data) {
        try {
            MqKafkaData<T> result = JSON.parseObject(data, MqKafkaData.class);
            return result;
        } catch (Exception e) {
            logger.error("", e);
            return null;
        }
    }

    @Override
    public void close() {

    }
}