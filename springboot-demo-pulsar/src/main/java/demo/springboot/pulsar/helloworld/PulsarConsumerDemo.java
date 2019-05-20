package demo.springboot.pulsar.helloworld;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.PulsarClient;

/**
 *
 *@author: bilahepan
 *@date: 2019/5/1 上午12:24
 */
public class PulsarConsumerDemo {
    private static String localClusterUrl = "pulsar://localhost:6650";
    public static void main(String[] args) {
        try {
            //将订阅消费者指定的主题和订阅
            Consumer<byte[]> consumer = getClient().newConsumer()
                    .topic("persistent://my-tenant/my-namespace/my-topic")
                    .subscriptionName("my-subscription")
                    .subscribe();
            while (true) {
                Message msg = consumer.receive();
                System.out.printf("consumer-Message received: %s. \n", new String(msg.getData()));
                // 确认消息，以便broker删除消息
                consumer.acknowledge(msg);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static PulsarClient getClient() throws Exception {
        PulsarClient client;
        client = PulsarClient.builder().serviceUrl(localClusterUrl).build();
        return client;
    }
}