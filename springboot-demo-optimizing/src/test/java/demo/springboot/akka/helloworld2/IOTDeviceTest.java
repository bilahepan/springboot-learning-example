package demo.springboot.akka.helloworld2;

import akka.actor.ActorRef;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;

import static junit.framework.TestCase.assertEquals;

/**
 * @author: bilahepan
 * @date: 2018/12/3 下午9:33
 */
public class IOTDeviceTest {

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
        public void testReplyWithEmptyReadingIfNoTemperatureIsKnown() {
            TestKit probe = new TestKit(system);
            ActorRef deviceActor = system.actorOf(Device.props("group", "device"));
            deviceActor.tell(new Device.ReadTemperature(42L), probe.getRef());
            Device.RespondTemperature response = probe.expectMsgClass(Device.RespondTemperature.class);
            assertEquals(42L, response.requestId);
            assertEquals(Optional.empty(), response.value);
        }

//    @Test
//    public void testReplyWithLatestTemperatureReading() {
//        TestKit probe = new TestKit(system);
//        ActorRef deviceActor = system.actorOf(Device.props("group", "device"));
//
//        deviceActor.tell(new Device.RecordTemperature(1L, 24.0), probe.getRef());
//        assertEquals(1L, probe.expectMsgClass(Device.TemperatureRecorded.class).requestId);
//
//        deviceActor.tell(new Device.ReadTemperature(2L), probe.getRef());
//        Device.RespondTemperature response1 = probe.expectMsgClass(Device.RespondTemperature.class);
//        assertEquals(2L, response1.requestId);
//        assertEquals(Optional.of(24.0), response1.value);
//
//        deviceActor.tell(new Device.RecordTemperature(3L, 55.0), probe.getRef());
//        assertEquals(3L, probe.expectMsgClass(Device.TemperatureRecorded.class).requestId);
//
//        deviceActor.tell(new Device.ReadTemperature(4L), probe.getRef());
//        Device.RespondTemperature response2 = probe.expectMsgClass(Device.RespondTemperature.class);
//        assertEquals(4L, response2.requestId);
//        assertEquals(Optional.of(55.0), response2.value);
//    }
}