package demo.springboot.pulsar.demo;

import demo.springboot.pulsar.thread.ThreadPoolFactory;
import org.apache.pulsar.client.api.*;
import org.apache.pulsar.common.api.proto.PulsarApi;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/1/3 上午9:42
 */
public class PulsarConsumerDemo {

    private static String localClusterUrl = "pulsar://localhost:6650";

    //private static String localClusterUrl = "pulsar://192.168.12.229:6650";
    //./bin/pulsar-admin topics create-partitioned-topic persistent://my-tenant/my-namespace/my-topic -p 3

    public static void main(String[] args) {
        try {
            //将订阅消费者指定的主题和订阅
            Consumer<byte[]> consumer = getClient().newConsumer()
                    //.topic("persistent://my-tenant/my-namespace/my-topic")
                    .topic("persistent://test-tenant-1/test-namespace-1/test-topic-1")
                    .subscriptionName("my-subscription-1").subscriptionType(SubscriptionType.Failover)
                    .deadLetterPolicy(DeadLetterPolicy.builder().maxRedeliverCount(3).build())
                    .ackTimeout(2, TimeUnit.SECONDS)
                    .subscribe();
            while (true) {
                Message msg = consumer.receive();
                System.out.println("--here--");
                System.err.printf("consumer-Message received: %s. \n", new String(msg.getData()));
                // 确认消息，以便broker删除消息
                consumer.acknowledge(msg);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

//        ThreadPoolFactory.getCustomThreadPool().execute(new Runnable() {
//            @Override
//            public void run() {
//                consumer1();
//            }
//        });
//
//
//        ThreadPoolFactory.getCustomThreadPool().execute(new Runnable() {
//            @Override
//            public void run() {
//                consumer2();
//            }
//        });
    }


    private static void consumer1() {
        try {
            //将订阅消费者指定的主题和订阅
            Consumer<byte[]> consumer = getClient().newConsumer()
                    .topic("persistent://public/pulsar-cluster/default/my-topic")
                    .subscriptionName("my-subscription-1").subscriptionType(SubscriptionType.Failover)
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
                    .topic("persistent://public/pulsar-cluster/default/my-topic")
                    .subscriptionName("my-subscription-1").subscriptionType(SubscriptionType.Failover)
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
                    .topic("persistent://public/pulsar-cluster/default/my-topic")
                    .subscriptionName("my-subscription-3").subscriptionType(SubscriptionType.Shared)
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


//-------------------------------------//
//订阅多个主题
//    public static void consumerMultiTopic() throws Exception {
//        ConsumerBuilder consumerBuilder = getClient().newConsumer()
//                .subscriptionName("my-subscription");
//
//        // 订阅命名空间中的所有主题
//        Pattern allTopicsInNamespace = Pattern.compile("persistent://public/default/.*");
//        Consumer allTopicsConsumer = consumerBuilder
//                .topicsPattern(allTopicsInNamespace)
//                .subscribe();
//
//        // 使用regex订阅命名空间中的主题子集
//        //        Pattern someTopicsInNamespace = Pattern.compile("persistent://public/default/foo.*");
//        //        Consumer allTopicsConsumer = consumerBuilder
//        //                .topicsPattern(someTopicsInNamespace)
//        //                .subscribe();
//
//
//        // 多主题订阅示例2
//        //        List<String> topics = Arrays.asList(
//        //                "topic-1",
//        //                "topic-2",
//        //                "topic-3"
//        //        );
//        //        Consumer multiTopicConsumer = consumerBuilder
//        //                .topics(topics)
//        //                .subscribe();
//
//        //多主题订阅示例3
//        //        Consumer multiTopicConsumer = consumerBuilder
//        //                .topics(
//        //                        "topic-1",
//        //                        "topic-2",
//        //                        "topic-3"
//        //                )
//        //                .subscribe();
//    }
//-------------------------------------//


//-------------------------------------//
//consumer 配置
//consumer 配置详情 http://pulsar.apache.org/api/client/org/apache/pulsar/client/api/ConsumerBuilder
//-------------------------------------//


//-------------------------------------//
// 异步接收
//    CompletableFuture<Message> asyncMessage = consumer.receiveAsync();
//-------------------------------------//