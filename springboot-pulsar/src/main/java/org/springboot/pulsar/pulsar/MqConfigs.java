package org.springboot.pulsar.pulsar;


import org.springboot.pulsar.pulsar.mq.MqEnv;

public final class MqConfigs {
    public static final String CN_WSS_SERVER_URL = "wss://mqe.tuyacn.com:8285/";
    public static final String US_WSS_SERVER_URL = "wss://mqe.tuyaus.com:8285/";
    public static final String EU_WSS_SERVER_URL = "wss://mqe.tuyaeu.com:8285/";
    public static final String IND_WSS_SERVER_URL = "wss://mqe.tuyain.com:8285/";


    //if enable, log the time spent on message processing
    public static final boolean DEBUG = true;

    public static String topicEnv = MqEnv.PROD.getValue();


    //these parameters are required
    public static String accessId = "";
    public static String accessKey = "";
    public static String wssServerUrl = CN_WSS_SERVER_URL;
}
