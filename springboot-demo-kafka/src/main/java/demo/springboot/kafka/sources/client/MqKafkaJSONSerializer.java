package demo.springboot.kafka.sources.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import demo.springboot.kafka.sources.basic.domain.MqKafkaData;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 *@author: 文若[gaotc@tuya.com]
 *@date: 2019/7/29 下午11:18
 */
public class MqKafkaJSONSerializer<T> implements Serializer<MqKafkaData<T>> {

    protected static Logger logger = LoggerFactory.getLogger(MqKafkaJSONSerializer.class);

    private String encoding = "UTF8";

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, MqKafkaData<T> data) {
        try {
            return JSON.toJSONBytes(data, SerializerFeature.WriteClassName);
        } catch (Exception e) {
            String str = "未知异常";
            if (data != null) {
                str = data.toString();
            }
            logger.error(str, e);
            return new byte[0];
        }
    }

    @Override
    public void close() {

    }
}