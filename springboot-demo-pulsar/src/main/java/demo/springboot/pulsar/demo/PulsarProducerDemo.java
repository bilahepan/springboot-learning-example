package demo.springboot.pulsar.demo;

import org.apache.pulsar.client.api.*;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/1/3 下午4:51
 */
public class PulsarProducerDemo {
    private static String localClusterUrl = "pulsar://localhost:6650";
    //private static String localClusterUrl = "pulsar://192.168.12.229:6650";

    public static void main(String[] args) {
        try {
            Producer<byte[]> producer = getProducer();
            // 然后你就可以发送消息到指定的broker 和topic上：
            String msg = "My message!";

            for (int i = 0; i < 1; i++) {
                //发送普通消息
                Long start = System.currentTimeMillis();
                MessageId msgId = producer.send((msg + i).getBytes());
                System.err.println("spend=" + (System.currentTimeMillis() - start) + ";send a message msgId = " + msgId.toString());

            }
            //./bin/pulsar-admin topics create-partitioned-topic persistent://my-tenant/my-namespace/my-topic -p 3
            //发送带元数据的消息
            /*
            Long start = System.currentTimeMillis();
            TypedMessageBuilder messageBuilder = producer.newMessage().key("devId").value(msg.getBytes());

            MessageId msgId = messageBuilder.send();
            System.err.println("spend=" + (System.currentTimeMillis() - start) + ";send a message msgId = " + msgId.toString());
            */
            producer.close();

        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static Producer<byte[]> getProducer() throws Exception {
        PulsarClient client;
        client = PulsarClient.builder().serviceUrl(localClusterUrl).build();

        //admin.namespaces().grantPermissionOnNamespace(nameSpaceName,role,action);

        //persistent://test-tenant-1/test-namespace-1/test-topic-1
        //Producer<byte[]> producer = client.newProducer().topic("persistent://my-tenant/my-namespace/my-topic").producerName("producerName").create();
        Producer<byte[]> producer = client.newProducer().topic("persistent://test-tenant-1/test-namespace-1/test-topic-1").producerName("producerName").create();
        return producer;
    }

}
