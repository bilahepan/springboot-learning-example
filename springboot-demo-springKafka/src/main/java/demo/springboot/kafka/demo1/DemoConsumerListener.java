package demo.springboot.kafka.demo1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/8/11 下午9:00
 */

@Component
public class DemoConsumerListener {
    private static final Logger log = LoggerFactory.getLogger(DemoConsumerListener.class);

    //声明consumerID为demo，监听topicName为topic.quick.demo的Topic
    @KafkaListener(id = "demo", topics = "topic.quick.demo")
    public void listen(String msgData) {
        log.info("demo receive : " + msgData);
    }
}