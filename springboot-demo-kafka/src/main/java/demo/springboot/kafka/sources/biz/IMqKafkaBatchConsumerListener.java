package demo.springboot.kafka.sources.biz;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.BatchMessageListener;

import java.util.List;

/**
 * 批量消费监听器
 *
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/8/6 下午8:20
 */
public interface IMqKafkaBatchConsumerListener<K, V> extends BatchMessageListener<K, V> {

    @Override
    void onMessage(List<ConsumerRecord<K, V>> data);
}