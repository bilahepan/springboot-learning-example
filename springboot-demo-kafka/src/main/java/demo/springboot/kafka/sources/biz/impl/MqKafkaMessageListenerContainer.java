package demo.springboot.kafka.sources.biz.impl;

import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/7/26 上午8:10
 */
public class MqKafkaMessageListenerContainer extends ConcurrentMessageListenerContainer {

    public MqKafkaMessageListenerContainer(ConsumerFactory consumerFactory, ContainerProperties containerProperties) {
        super(consumerFactory, containerProperties);
    }

    public void doStart() {
        super.doStart();
    }

    public void doStop() {
        super.stop();
    }
}