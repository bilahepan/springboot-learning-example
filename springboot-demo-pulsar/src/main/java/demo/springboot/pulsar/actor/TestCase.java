package demo.springboot.pulsar.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import java.util.concurrent.ThreadFactory;

/**
 * @author: bilahepan
 * @date: 2019/1/10 下午10:22
 */
public class TestCase {

    public static void main(String[] args) {
        final ActorSystem system = ActorSystem.create("helloakka");
        try {
            //ActorRef
            /**使用工厂创建Actor实例。工厂不返回一个actor实例，而是一个akka.actor.ActorRef指向actor实例的引用。
             * 这种间接级别在分布式系统中增加了很多功能和灵活性。*/
            //ActorSystem
            /**该akka.actor.ActorSystem工厂是，在一定程度上，类似于Spring的BeanFactory。
             * 它充当Actor的容器并管理他们的生命周期。
             * 该actorOf工厂方法创建Actor采用两个参数，一个配置对象Props和一个名称，这个名称也很重要。
             */
            //Actor消息驱动模型
            /**Actor是被动的和消息驱动的。在收到消息之前，Actor不会执行任何操作。
             * Actor使用异步消息进行通信。这可确保发件人不会等待收件人处理其邮件。
             * 相反，发件人将邮件放在收件人的邮箱中，并可以自由地执行其他工作。
             * Actor的邮箱本质上是一个带有排序语义的消息队列。
             * 保存从同一个Actor发送的多个消息的顺序，但可以与另一个Actor发送的消息交错。
             *
             * Actor不处理消息时，它处于挂起状态，除了内存之外它不消耗任何资源。再次展示了Actors的轻量级，高效性。
             */
            for (int i = 0; i < 100; i++) {
                String msg = "a-" + i;
                //创建Actor
                ActorRef producerActor =
                        system.actorOf(ProducerActor.props(), "ProducerActor");
                System.err.println(producerActor.getClass().toString());
                //发送消息
                /**要将消息放入Actor的邮箱，使用该ActorRef的tell方法*/
                //tell
                /**tell()方法是异步的，它只给Actor的邮箱放一封邮件，然后就返回了。
                 * tell()方法的第一个参数是消息，第二个参数是发送者，这样，接收者Actor就知道是谁给自己发的消息了。
                 * printerActor并不关心消息是谁发送的，所以我们给它传了ActorRef.noSender()。
                 */
                //producerActor.tell(new ProducerActor.MessageData(msg), ActorRef.noSender());
            }

        } catch (Exception ioe) {
        } finally {
            system.terminate();
        }
    }

}