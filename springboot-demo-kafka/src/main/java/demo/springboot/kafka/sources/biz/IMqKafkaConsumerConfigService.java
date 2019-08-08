package demo.springboot.kafka.sources.biz;

import demo.springboot.kafka.sources.basic.domain.MqKafkaConsumerConfig;
import demo.springboot.kafka.sources.basic.domain.MqKafkaConsumerConfigServer;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/8/6 下午8:08
 */


public interface IMqKafkaConsumerConfigService<C extends MqKafkaConsumerConfigServer> extends IMqKafkaConfigService<C> {

    /**
     * 获取topic的配置信息
     */
    MqKafkaConsumerConfig getConsumerConfig(String topic);
}