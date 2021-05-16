package demo.springboot.localdebug;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.PulsarClient;

public class ConsumerDemo {


    public static void main(String[] args) {
        try {
            //将订阅消费者指定的主题消息
            Consumer<byte[]> consumer =
                    getClient().newConsumer().topic(LocalDebugConfig.topicName).subscriptionName("my-subscription")
                            .subscribe();
            while (true) {
                Message msg = consumer.receive();
                System.out.printf("consumer-Message received: %s. \n", new String(
                        msg.getData()));
                // 确认消息，以便便broker删除消息
                consumer.acknowledge(msg);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static PulsarClient getClient() throws Exception {
        PulsarClient client;
        client = PulsarClient.builder().serviceUrl(LocalDebugConfig.localClusterUrl).build();
        return client;
    }
}
