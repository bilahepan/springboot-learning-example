package demo.springboot.pulsar.sample;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.impl.TopicMessageIdImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: bilahepan
 * @date: 2019/4/16 下午5:48
 */
public class MessageHandlerTask implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(MessageHandlerTask.class);

    private Consumer consumer;
    private Integer consumerNum;

    public MessageHandlerTask(Integer consumerNum, Consumer consumer) {
        this.consumerNum = consumerNum;
        this.consumer = consumer;
    }

    @Override
    public void run() {
        do {
            try {
                Message message = consumer.receive();
                System.out.println("----------------consumerNum:" + consumerNum + ";consumerName:" + consumer.getConsumerName() + "--------------------\n"
                        +"Message Received:" + new String(message.getData()) +"\n"+",seq="
                        + message.getSequenceId() + ",time=" + message.getPublishTime() + ",consumed time="
                        + System.currentTimeMillis() + ",partition="
                        + ((TopicMessageIdImpl) message.getMessageId()).getTopicPartitionName());

                consumer.acknowledge(message);
            } catch (Throwable t) {
                System.out.println("error:" + t);
            }
        } while (true);
    }
}