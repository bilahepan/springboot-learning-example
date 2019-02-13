package demo.springboot.pulsar.sdk.consumer;

import demo.springboot.pulsar.sdk.MqConsumer;
import demo.springboot.pulsar.sdk.MqEnv;
import org.apache.pulsar.client.impl.TopicMessageIdImpl;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/1/23 上午11:45
 */
public class ConsumerExample {

//        String url = "pulsar+ssl://mqe.tuyacn.com:7285/";
//        String accessId = "j5eegctww4yvawmkqpk4";
//        String accessKey = "9aquctcxet9e93k9trrw5gtprfjyyryf";
//
//        MqConsumer mqConsumer = MqConsumer.build().serviceUrl(url).accessId(accessId).accessKey(accessKey)
//                .maxRedeliverCount(3).env(MqEnv.TEST).messageListener(message -> {
//                    String secretKey = "9aquctcxet9e93k9trrw5gtprfjyyryf".substring(8, 24);
//                    //String msg = AESBase64Utils.decrypt(new String(message.getData()), secretKey);
//                    String msg = new String(message.getData());
//                    System.out.println("Message received:" + msg + ",seq="
//                            + message.getSequenceId() + ",time=" + message.getPublishTime() + ",consumed time="
//                            + System.currentTimeMillis() + ",partition="
//                            + ((TopicMessageIdImpl) message.getMessageId()).getTopicPartitionName());
//                });
//        mqConsumer.start();
//    }


    public static void main(String[] args) throws Exception {
        String url = "pulsar+ssl://mqe.tuyacn.com:7285/";
        String accessId = "7pdhxawdxmwa4q5u778k";
        String accessKey = "qk9acfmnkndwmmpykd9ryca8cxduyuf9";
        //---test
//        String accessId = "j5eegctww4yvawmkqpk4";
//        String accessKey = "9aquctcxet9e93k9trrw5gtprfjyyryf";

        MqConsumer mqConsumer = MqConsumer.build().serviceUrl(url).accessId(accessId).accessKey(accessKey).env(MqEnv.TEST)
                .maxRedeliverCount(3).messageListener(message -> {
                    System.out.println("Message received:" + new String(message.getData()));
//                    System.out.println("Message received:" + new String(message.getData()) + ",seq="
//                            + message.getSequenceId() + ",time=" + message.getPublishTime() + ",consumed time="
//                            + System.currentTimeMillis() + ",partition="
//                            + ((TopicMessageIdImpl) message.getMessageId()).getTopicPartitionName());
                });
        mqConsumer.start();
    }

}