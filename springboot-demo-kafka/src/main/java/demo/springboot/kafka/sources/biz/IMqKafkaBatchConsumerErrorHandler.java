package demo.springboot.kafka.sources.biz;

import demo.springboot.kafka.sources.basic.domain.MqKafkaConsumerConfigServer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.listener.BatchErrorHandler;

/**
 * 批量消费错误处理器
 *
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/8/7 上午10:43
 */
public interface IMqKafkaBatchConsumerErrorHandler extends BatchErrorHandler, IMqKafkaConsumerConfigService<MqKafkaConsumerConfigServer> {

    @Override
    void handle(Exception thrownException, ConsumerRecords<?, ?> data);

    @Override
    void setConfigServer(MqKafkaConsumerConfigServer configServer);

    @Override
    MqKafkaConsumerConfigServer getConfigServer();

}