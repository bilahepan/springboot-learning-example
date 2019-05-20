package demo.springboot.akka.helloworld;

/**
 * @author: bilahepan
 * @date: 2018/12/2 下午8:02
 */

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * 官方 helloworld 示例
 * https://developer.lightbend.com/guides/akka-quickstart-java/define-actors.html
 *@author: bilahepan
 *@date: 2018/12/2 下午8:12
 */
public class Printer extends AbstractActor {

    //无参构造器
    public Printer() {
    }

    /**props在Actor的类中使用静态方法描述如何构造Actor 也是一种常见的模式。*/
    /**静态props方法创建并返回一个Props实例。Props是一个配置类，用于指定创建actor的选项，
     * 将其视为不可变且因此可自由共享的配方，用于创建可包含关联部署信息的actor。
     * 此示例仅传递Actor在构造时所需的参数。*/
    //获取属性的静态方法
    static public Props props() {
        return Props.create(Printer.class, () -> new Printer());
    }


    @Override
    public Receive createReceive() {
        //receiveBuilder定义的行为; Actor如何对收到的不同消息做出反应。
        //只处理一种类型的消息Greeting，并记录该消息的内容。
        return receiveBuilder()
                .match(Greeting.class, greeting -> {
                    log.info(greeting.message);
                    System.out.println("hello world akka! message="+greeting.message);
                })
                .build();
    }

    /**
     * 将actor的关联消息作为静态类放在Actor的类中是一种很好的做法。这使得更容易理解actor期望和处理的消息类型。
     * */
    //静态内部类-问候者
    static public class Greeting {
        public final String message;

        public Greeting(String message) {
            this.message = message;
        }
    }

    private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
}