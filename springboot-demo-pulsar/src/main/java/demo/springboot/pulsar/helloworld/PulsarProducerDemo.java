package demo.springboot.pulsar.helloworld;

import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;

/**
 *
 *@author: bilahepan
 *@date: 2019/5/1 上午12:25
 */
public class PulsarProducerDemo {
    private static String localClusterUrl = "pulsar://localhost:6650";

    public static void main(String[] args) {
        try {
            Producer<byte[]> producer = getProducer();
            String msg = "hello world pulsar!";

            Long start = System.currentTimeMillis();
            MessageId msgId = producer.send(msg.getBytes());
            System.out.println("spend=" + (System.currentTimeMillis() - start) + ";send a message msgId = " + msgId.toString());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static Producer<byte[]> getProducer() throws Exception {
        PulsarClient client;
        client = PulsarClient.builder().serviceUrl(localClusterUrl).build();
        Producer<byte[]> producer = client.newProducer().topic("persistent://my-tenant/my-namespace/my-topic").producerName("producerName").create();
        return producer;
    }
}