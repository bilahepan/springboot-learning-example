package demo.springboot.pulsar.demo;

import demo.springboot.pulsar.thread.ThreadPoolFactory;
import org.apache.pulsar.client.api.*;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/1/3 上午9:42
 */
public class PulsarConsumerDemoMultiThread {

    private static String localClusterUrl = "pulsar://10.0.200.91:6650";

    private static String Topic_Name = "persistent://my-tenant/my-namespace/my-topic";

    private static String Subscription_Name = "my-subscription";

    public static void main(String[] args) {

        ThreadPoolFactory.getCustomThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                consumer1();
            }
        });

        ThreadPoolFactory.getCustomThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                consumer2();
            }
        });


        ThreadPoolFactory.getCustomThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                consumer3();
            }
        });
    }


    private static void consumer1() {
        try {
            //将订阅消费者指定的主题和订阅
            Consumer<byte[]> consumer = getClient().newConsumer()
                    .topic(Topic_Name)
                    .subscriptionName(Subscription_Name).subscriptionType(SubscriptionType.Failover)
                    .deadLetterPolicy(DeadLetterPolicy.builder().maxRedeliverCount(3).build())
                    .subscribe();
            while (true) {
                Message msg = consumer.receive();
                System.err.printf("consumer1-Message received: %s. \n", new String(msg.getData()));
                // 确认消息，以便broker删除消息
                consumer.acknowledge(msg);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    private static void consumer2() {
        try {
            //将订阅消费者指定的主题和订阅
            Consumer<byte[]> consumer = getClient().newConsumer()
                    .topic(Topic_Name)
                    .subscriptionName(Subscription_Name).subscriptionType(SubscriptionType.Failover)
                    .deadLetterPolicy(DeadLetterPolicy.builder().maxRedeliverCount(3).build())
                    .subscribe();
            while (true) {
                Message msg = consumer.receive();
                System.err.printf("consumer2-Message received: %s. \n", new String(msg.getData()));
                // 确认消息，以便broker删除消息
                consumer.acknowledge(msg);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    private static void consumer3() {
        try {
            //将订阅消费者指定的主题和订阅
            Consumer<byte[]> consumer = getClient().newConsumer()
                    .topic(Topic_Name)
                    .subscriptionName(Subscription_Name).subscriptionType(SubscriptionType.Failover)
                    .deadLetterPolicy(DeadLetterPolicy.builder().maxRedeliverCount(3).build())
                    .subscribe();
            while (true) {
                Message msg = consumer.receive();
                System.err.printf("consumer3-Message received: %s. \n", new String(msg.getData()));
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
