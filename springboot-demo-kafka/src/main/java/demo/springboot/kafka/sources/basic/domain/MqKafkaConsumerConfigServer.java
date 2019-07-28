package demo.springboot.kafka.sources.basic.domain;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/7/25 下午9:24
 */

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MqKafkaConsumerConfigServer {

    protected static Logger logger = LoggerFactory.getLogger(MqKafkaConsumerConfigServer.class);

    /**
     *
     * key:topic
     * value:MqKafkaConsumerConfig
     *
     * */
    protected Map<String, MqKafkaConsumerConfig> consumerConfigMap = null;

    /**
     * kafka原生配置信息
     */
    private Map<String, Object> nativeConsumerConfigs;

    public MqKafkaConsumerConfigServer(List<MqKafkaConsumerConfig> consumerConfigs) {
        setConsumerConfigs(consumerConfigs);
    }

    public MqKafkaConsumerConfigServer() {
    }

    public MqKafkaConsumerConfigServer(List<MqKafkaConsumerConfig> consumerConfigs, Map<String, Object> nativeConsumerConfigs) {
        setConsumerConfigs(consumerConfigs);
        this.nativeConsumerConfigs = nativeConsumerConfigs;
    }

    public void setConsumerConfigs(List<MqKafkaConsumerConfig> consumerConfigs) {
        if (CollectionUtils.isNotEmpty(consumerConfigs)) {
            consumerConfigMap = new HashMap<>();
            for (MqKafkaConsumerConfig kafkaConsumerConfig : consumerConfigs) {
                consumerConfigMap.put(kafkaConsumerConfig.getTopic(), kafkaConsumerConfig);
            }
        }
    }

    public MqKafkaConsumerConfig getConsumerConfig(String topic) {
        if (consumerConfigMap != null && consumerConfigMap.containsKey(topic)) {
            return consumerConfigMap.get(topic);
        }
        return null;
    }

    /**
     * 根据key获取kafka消费者原生配置的value
     *
     * @param key 原生配置key
     * @return 不存在则返回null
     */
    public Object getNativeConsumerConfigValue(String key) {
        return MapUtils.isNotEmpty(nativeConsumerConfigs) ? nativeConsumerConfigs.get(key) : null;
    }
}
