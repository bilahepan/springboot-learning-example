package org.springboot.pulsar.pulsar;


import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.java_websocket.WebSocket;
import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.FramedataImpl1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springboot.pulsar.pulsar.mq.IMessageHandler;
import org.springboot.pulsar.pulsar.mq.WssWebSocketClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newScheduledThreadPool;

@Component
public class WssWebSocketConsumer implements InitializingBean {

    @Resource
    private IMessageHandler messageHandler;

    private final String accessId = MqConfigs.accessId;
    private final String accessKey = MqConfigs.accessKey;
    private final String wssServerUrl = MqConfigs.wssServerUrl;

    private static Logger logger = LoggerFactory.getLogger(WssWebSocketConsumer.class);

    @Override
    public void afterPropertiesSet() throws URISyntaxException, InterruptedException {
        if (Objects.isNull(messageHandler)) {
            throw new IllegalArgumentException("messageHandler is null,please check!");
        }
        process();
    }

    public void process() throws URISyntaxException, InterruptedException {
        String accessId = this.accessId;
        String accessKey = this.accessKey;
        String wssServerUrl = this.wssServerUrl;

        if (StringUtils.isAnyBlank(accessId, accessKey, wssServerUrl)) {
            throw new IllegalArgumentException("please check the configuration parameters, accessId,accessKey,wssServerUrl is illegal!");
        }
        Map<String, String> httpHeaders = getHttpHeaders(accessId, accessKey);
        String topicUrl = getTopicUrl(wssServerUrl, accessId);
        ConcurrentHashMap<Integer, WssWebSocketClient> clientPoolMap = new ConcurrentHashMap<>(1);

        WssWebSocketClient wssClient = new WssWebSocketClient(new URI(topicUrl), httpHeaders, messageHandler);
        clientPoolMap.put(1, wssClient);
        clientPoolMap.get(1).connectBlocking();
        //
        ScheduledExecutorService schedulePool = newScheduledThreadPool(1);
        schedulePool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                WssWebSocketClient wssWebSocketClient = clientPoolMap.get(1);
                WebSocket.READYSTATE state = wssWebSocketClient.getReadyState();
                if (state != WebSocket.READYSTATE.OPEN) {
                    if (state == WebSocket.READYSTATE.CLOSED) {
                        try {
                            clientPoolMap.put(1, new WssWebSocketClient(new URI(topicUrl), httpHeaders, messageHandler));
                            clientPoolMap.get(1).connectBlocking();
                        } catch (InterruptedException | URISyntaxException e) {
                            logger.error("error, ", e);
                        }
                    }
                } else {
                    FramedataImpl1 frameData = new FramedataImpl1(Framedata.Opcode.PING);
                    frameData.setFin(true);
                    wssWebSocketClient.getConnection().sendFrame(frameData);
                    logger.info("webSocket check status is ok.");
                }
            }
        }, 30, 10, TimeUnit.SECONDS);
    }


    private String getPassword(String accessId, String accessKey) {
        return DigestUtils.md5Hex(accessId + DigestUtils.md5Hex(accessKey)).substring(8, 24);
    }


    private Map<String, String> getHttpHeaders(String accessId, String accessKey) {
        Map<String, String> map = new HashMap<>(3);
        map.put("username", accessId);
        map.put("password", getPassword(accessId, accessKey));
        return map;
    }


    private String getTopicUrl(String wssSeverUrl, String accessId) {
        String topicUrl = wssSeverUrl + "ws/v2/consumer/persistent/" + accessId + "/out/" + MqConfigs.topicEnv + "/" + accessId + "-sub" +
                "?ackTimeoutMillis=3000&subscriptionType=Failover";
        return topicUrl;
    }
}
