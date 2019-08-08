package demo.springboot.kafka.sources.biz;

import demo.springboot.kafka.sources.basic.domain.MqKafkaConsumerConfigServer;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.List;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/8/6 下午8:23
 */
public interface IMqKafkaBatchConsumerConfigListener<K, V> extends IMqKafkaConsumerConfigService<MqKafkaConsumerConfigServer>
        , IMqKafkaBatchConsumerListener<K, V> {

    @Override
    void onMessage(List<ConsumerRecord<K, V>> data);

    @Override
    void setConfigServer(MqKafkaConsumerConfigServer configServer);

    @Override
    MqKafkaConsumerConfigServer getConfigServer();

}