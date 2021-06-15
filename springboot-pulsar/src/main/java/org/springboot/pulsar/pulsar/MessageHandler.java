package org.springboot.pulsar.pulsar;


import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springboot.pulsar.pulsar.mq.AESBase64Utils;
import org.springboot.pulsar.pulsar.mq.IMessageHandler;
import org.springboot.pulsar.pulsar.mq.MessageVO;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

@Service(value = "messageHandler")
public class MessageHandler implements IMessageHandler {
    private static Logger logger = LoggerFactory.getLogger(MessageHandler.class);
    final static BASE64Decoder decoder = new BASE64Decoder();

    @Override
    public void handler(String payload) {
        try {
            String jsonMessage = new String(new String(decoder.decodeBuffer(payload), "UTF-8"));
            logger.info("receive msg, jsonMessage={}", new String(decoder.decodeBuffer(payload), "UTF-8"));
            MessageVO vo = JSON.parseObject(jsonMessage, MessageVO.class);
            logger.info("the real message data:" + AESBase64Utils.decrypt(vo.getData(), MqConfigs.accessKey.substring(8, 24)));
            //TODO process the messages you receive，business logic is implemented in this method
        } catch (Exception e) {
            logger.error("error ,", e);
        }
    }
}
