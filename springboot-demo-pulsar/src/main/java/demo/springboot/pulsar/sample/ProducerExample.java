package demo.springboot.pulsar.sample;

import org.apache.pulsar.client.api.*;

/**
 * @author: bilahepan
 * @date: 2019/1/3 下午4:51
 */
public class ProducerExample {

    public static void main(String[] args) {
        try {
            Producer<byte[]> producer = getProducer();
            String extContent = "msg.";
            String msg = "My message! " + "extContent=" + extContent + "my num is =";
            for (int i = 1; i <= 1000000; i++) {
                TypedMessageBuilder messageBuilder = producer.newMessage().key(i + "").value((msg + i).toString().getBytes());
                //发送普通消息
                Long start = System.currentTimeMillis();
                MessageId messageId = messageBuilder.send();
                System.out.println("spend=" + (System.currentTimeMillis() - start));
            }
            producer.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static Producer<byte[]> getProducer() throws Exception {
        PulsarClient client;
        client = PulsarClient.builder().serviceUrl(MqConfigs.serviceUrl).build();
        Producer<byte[]> producer = client.newProducer().topic(MqConfigs.Topic_Name)
                .messageRoutingMode(
                        MessageRoutingMode.CustomPartition).messageRouter(new MessageRouter() {
                    @Override
                    public int choosePartition(Message<?> message, TopicMetadata metadata) {
                        System.out.println("key=" + message.getKey() + ";" + "hash=" + Math.abs(message.getKey().hashCode()) % 3);
                        return Math.abs(message.getKey().hashCode()) % MqConfigs.PARTITION_NUM;
                    }
                }).create();
        return producer;
    }

}
