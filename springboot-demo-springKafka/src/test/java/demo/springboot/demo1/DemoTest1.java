package demo.springboot.demo1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/8/11 下午9:07
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class DemoTest1 {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Test
    public void testSend() throws InterruptedException {
        //KafkaTemplate在发送的时候就已经帮我们完成了创建的操作，
        //所以我们不需要主动创建"topic.quick.demo"这个Topic，而是交由KafkaTemplate去完成。
        //但这样也出现了问题，这种情况创建出来的Topic的Partition(分区)数永远只有1个。
        kafkaTemplate.send("topic.quick.demo", "this is my first demo");

        //休眠5秒，为了使监听器有足够的时间监听到topic的数据
        Thread.sleep(5000);
    }
}