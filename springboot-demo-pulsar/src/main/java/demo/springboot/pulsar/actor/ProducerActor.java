package demo.springboot.pulsar.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.apache.pulsar.client.api.MessageId;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;

/**
 * @author: bilahepan
 * @date: 2019/1/10 下午10:18
 */
public class ProducerActor extends AbstractActor {
    //private static String localClusterUrl = "pulsar://localhost:6650";
    private static String localClusterUrl = "pulsar://192.168.12.229:6650";
    //-------------------------------------//

    //无参构造器
    public ProducerActor() {
    }

    /**props在Actor的类中使用静态方法描述如何构造Actor 也是一种常见的模式。*/
    /**
     * 静态props方法创建并返回一个Props实例。Props是一个配置类，用于指定创建actor的选项，
     * 将其视为不可变且因此可自由共享的配方，用于创建可包含关联部署信息的actor。
     * 此示例仅传递Actor在构造时所需的参数。
     */
    //获取属性的静态方法
    static public Props props() {
        return Props.create(ProducerActor.class, () -> new ProducerActor());
    }

    @Override
    public Receive createReceive() {
        //receiveBuilder定义的行为; Actor如何对收到的不同消息做出反应。
        //只处理一种类型的消息Greeting，并记录该消息的内容。
        return receiveBuilder()
                .match(MessageData.class, msg -> {
                    //-------------------------------------//
                    //生产者
                    send(msg.message);
                    //-------------------------------------//

                    System.out.println("hello world akka! message=" + msg.message);
                })
                .build();
    }

    /**
     * 将actor的关联消息作为静态类放在Actor的类中是一种很好的做法。这使得更容易理解actor期望和处理的消息类型。
     */
    //静态内部类-问候者
    static public class MessageData {
        public final String message;

        public MessageData(String message) {
            this.message = message;
        }
    }

    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    //生产者
    //-------------------------------------//
    public void send(String msg) {
        try {
            Producer<byte[]> producer = getProducer();
            // 然后你就可以发送消息到指定的broker 和topic上：
            Long start = System.currentTimeMillis();
            MessageId msgId = producer.send(msg.getBytes());
            System.err.println("spend=" + (System.currentTimeMillis() - start) + ";send a message msgId = " + msgId.toString());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public Producer<byte[]> getProducer() throws Exception {
        PulsarClient client;
        client = PulsarClient.builder().serviceUrl(localClusterUrl).build();
        Producer<byte[]> producer = client.newProducer().topic("persistent://public/pulsar-cluster/default/my-topic").create();
        return producer;
    }
}