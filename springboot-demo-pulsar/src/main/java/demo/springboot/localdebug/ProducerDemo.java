package demo.springboot.localdebug;


import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;

public class ProducerDemo {

    public static void main(String[] args) {
        try {
            Producer<byte[]> producer = getProducer();
            String msg = "hello world pulsar!";
            Long start = System.currentTimeMillis();
            MessageId
                    msgId = producer.send(msg.getBytes());
            System.out.println("spend=" + (System.currentTimeMillis() - start) + ";send a message msgId = " + msgId.toString());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static Producer<byte[]> getProducer() throws Exception {
        PulsarClient
                client;
        client = PulsarClient.builder().serviceUrl(LocalDebugConfig.localClusterUrl).build();

        Producer<byte[]> producer =
                client.newProducer().topic(LocalDebugConfig.topicName).producerName("producerName").create();
        return producer;
    }
}
