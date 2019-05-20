package demo.springboot.pulsar.multithread;

/**
 * @author: bilahepan
 * @date: 2019/4/28 下午9:06
 */
public class MqConfigs {

    public final static String serviceUrl = "pulsar://10.0.200.90:6650";
    public final static String Topic_Name = "persistent://my-tenant/my-namespace/my-topic";
    public final static String Subscription_Name = "my-subscription";


    public final static Integer MAX_REDELIVER_COUNT = 3;
    public final static Integer PARTITION_NUM = 11;

}