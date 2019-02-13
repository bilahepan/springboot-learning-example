package demo.springboot.akka.helloworld;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2018/12/2 下午8:13
 */

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import demo.springboot.akka.helloworld.Printer.Greeting;//包含问候语的消息

public class Greeter extends AbstractActor {
    //成员属性
    private final String message;
    private final ActorRef printerActor;
    private String greeting = "";

    //构造器
    public Greeter(String message, ActorRef printerActor) {
        this.message = message;
        this.printerActor = printerActor;
    }

    /**props在Actor的类中使用静态方法描述如何构造Actor 也是一种常见的模式。*/
    /**静态props方法创建并返回一个Props实例。Props是一个配置类，用于指定创建actor的选项，
     * 将其视为不可变且因此可自由共享的配方，用于创建可包含关联部署信息的actor。
     * 此示例仅传递Actor在构造时所需的参数。*/
    //属性配置方法
    static public Props props(String message, ActorRef printerActor) {
        return Props.create(Greeter.class, () -> new Greeter(message, printerActor));
    }


    @Override
    public Receive createReceive() {
        //receiveBuilder定义的行为; Actor如何对收到的不同消息做出反应。
        //在这种情况下Greeter，它需要两种类型的消息：WhoToGreet和Greet。
        //前者将更新greeting Actor 的状态，后者将触发发送greeting给Printer Actor。
        return receiveBuilder()
                .match(WhoToGreet.class, wtg -> {
                    this.greeting = message + ", " + wtg.who;
                })
                .match(Greet.class, x -> {
                    printerActor.tell(new Greeting(greeting), getSelf());
                })
                .build();
    }

    /**
     * 将actor的关联消息作为静态类放在Actor的类中是一种很好的做法。这使得更容易理解actor期望和处理的消息类型。
     * */
    //静态内部类-问候的收件人
    static public class WhoToGreet {
        public final String who;

        public WhoToGreet(String who) {
            this.who = who;
        }
    }

    //静态内部类-执行问候语的指令
    static public class Greet {
        public Greet() {
        }
    }
}
