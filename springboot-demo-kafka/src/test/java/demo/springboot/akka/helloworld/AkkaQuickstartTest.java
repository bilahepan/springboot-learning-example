package demo.springboot.akka.helloworld;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 *
 *@author: 文若[gaotc@tuya.com]
 *@date: 2018/12/2 下午10:12
 */
public class AkkaQuickstartTest {
    static ActorSystem system;

    @BeforeClass
    public static void setup() {
        system = ActorSystem.create();
    }

    @AfterClass
    public static void teardown() {
        TestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    public void testGreeterActorSendingOfGreeting() {
        final TestKit testProbe = new TestKit(system);
        final ActorRef helloGreeter = system.actorOf(Greeter.props("Hello", testProbe.getRef()));
        helloGreeter.tell(new Greeter.WhoToGreet("Akka"), ActorRef.noSender());
        helloGreeter.tell(new Greeter.Greet(), ActorRef.noSender());
        Printer.Greeting greeting = testProbe.expectMsgClass(Printer.Greeting.class);
        assertEquals("Hello, Akka", greeting.message);
    }
}