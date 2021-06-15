package org.springboot.pulsar.pulsar.mq;

import java.net.URI;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.alibaba.fastjson.JSON;
import org.java_websocket.WebSocket;
import org.java_websocket.client.DefaultSSLWebSocketClientFactory;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.*;
import org.java_websocket.framing.Framedata;
import org.java_websocket.framing.FramedataImpl1;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springboot.pulsar.pulsar.MqConfigs;

public class WssWebSocketClient extends WebSocketClient {
    private static Logger logger = LoggerFactory.getLogger(WssWebSocketClient.class);
    private IMessageHandler messageHandler;

    public WssWebSocketClient(URI serverURI, Map<String, String> headers, IMessageHandler messageHandler) {
        super(serverURI, new Draft_17(), headers, 3000);
        if (serverURI.toString().contains("wss://")) {
            trustAllHosts(this);
        }
        this.messageHandler = messageHandler;
    }


    //code, reason, remote
    @Override
    public void onClose(int code, String reason, boolean remote) {
        logger.info("onClose, " + code + ", " + reason);
        this.close();
    }

    @Override
    public void onError(Exception e) {
        logger.error("onError, e=", e);
        this.close();
    }

    /**
     * listen for messages, consume and process
     */
    @Override
    public void onMessage(String message) {
        WssMessage msg = new WssMessage();
        msg = JSON.parseObject(message, WssMessage.class);
        String payload = msg.getPayload();
        try {
            Long s = System.currentTimeMillis();
            messageHandler.handler(payload);
            if (MqConfigs.DEBUG) {
                logger.info("business processing cost={}", System.currentTimeMillis() - s);
            }
        } catch (Exception e) {
            logger.error("error ,", e);
        }
        HashMap<String, String> ackRes = new HashMap<>(1);
        ackRes.put("messageId", msg.getMessageId());
        this.send(JSON.toJSONString(ackRes));
        logger.info("ack success.messageId=" + msg.getMessageId());
    }


    @Override
    public void onWebsocketPing(WebSocket conn, Framedata f) {
        logger.info("receive ping,frameData={}", f.toString());
        FramedataImpl1 frameData = new FramedataImpl1(Framedata.Opcode.PONG);
        frameData.setFin(true);
        this.getConnection().sendFrame(frameData);
    }


    /**
     * This default implementation does not do anything. Go ahead and overwrite it.
     *
     * @see @see org.java_websocket.WebSocketListener#onWebsocketPong(WebSocket, Framedata)
     */
    @Override
    public void onWebsocketPong(WebSocket conn, Framedata f) {
        logger.info("receive pong.");
    }


    @Override
    public void onOpen(ServerHandshake handshake) {
        logger.info("onOpen, " + JSON.toJSONString(handshake) + "t=" + System.currentTimeMillis());
    }

    private void trustAllHosts(WssWebSocketClient appClient) {
        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[] {};
            }

            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                // Auto-generated method stub
            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                // Auto-generated method stub
            }
        }};

        try {
            //The server supports TLSv1.2
            SSLContext sc = SSLContext.getInstance("TLSv1.2");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            appClient.setWebSocketFactory(new DefaultSSLWebSocketClientFactory(sc));
        } catch (Exception e) {
            logger.error("error , ", e);
        }
    }
}