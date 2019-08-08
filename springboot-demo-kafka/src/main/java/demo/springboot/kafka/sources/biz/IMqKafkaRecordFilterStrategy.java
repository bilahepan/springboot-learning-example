package demo.springboot.kafka.sources.biz;

import demo.springboot.kafka.sources.basic.domain.MqKafkaConsumerConfigServer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;

/**
 * 过滤策略
 *
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/8/6 下午9:09
 */
public interface IMqKafkaRecordFilterStrategy<K, V> extends RecordFilterStrategy<K, V>
        , IMqKafkaConsumerConfigService<MqKafkaConsumerConfigServer> {


    @Override
    boolean filter(ConsumerRecord<K, V> consumerRecord);

    @Override
    void setConfigServer(MqKafkaConsumerConfigServer configServer);


    @Override
    MqKafkaConsumerConfigServer getConfigServer();

}