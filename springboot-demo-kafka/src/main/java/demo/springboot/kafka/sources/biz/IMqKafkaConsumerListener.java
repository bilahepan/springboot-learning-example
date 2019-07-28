package demo.springboot.kafka.sources.biz;

import demo.springboot.kafka.sources.basic.domain.MqKafkaConsumerConfigServer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.support.Acknowledgment;

/**
 *
 *@author: 文若[gaotc@tuya.com]
 *@date: 2019/7/25 下午9:22
 */
public interface IMqKafkaConsumerListener<String,T> {

    public void setKafkaConsumerConfigServer(MqKafkaConsumerConfigServer kafkaConsumerConfigServer);

    public void onMessage(ConsumerRecord<String, T> record);

    public void onMessage(ConsumerRecord<String, T> record, Acknowledgment acknowledgment);

}
