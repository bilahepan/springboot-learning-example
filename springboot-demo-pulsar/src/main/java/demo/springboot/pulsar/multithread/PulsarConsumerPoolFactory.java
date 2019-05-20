package demo.springboot.pulsar.multithread;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.DeadLetterPolicy;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.SubscriptionType;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: bilahepan
 * @date: 2019/4/16 下午6:41
 */
public class PulsarConsumerPoolFactory {

    public static ConcurrentHashMap<Integer, Consumer> getConsumerPool() {
        return InitializingConsumerPool.pool;
    }


    private static class InitializingConsumerPool {
        private static final ConcurrentHashMap<Integer, Consumer> pool = new ConcurrentHashMap<Integer, Consumer>() {{
            for (int i = 1; i <= MqConfigs.PARTITION_NUM; i++) {
                put(i, getConsumerInstance());
            }
        }};


        private static Consumer getConsumerInstance() {
            try {
                return PulsarClientFactory.getPulsarClient().newConsumer().topic(MqConfigs.Topic_Name)
                        .subscriptionName(MqConfigs.Subscription_Name).subscriptionType(SubscriptionType.Failover)
                        .deadLetterPolicy(DeadLetterPolicy.builder().maxRedeliverCount(MqConfigs.MAX_REDELIVER_COUNT).build()).subscribe();
            } catch (PulsarClientException e) {
                System.err.println("PulsarConsumerPoolFactory init error! e=" + e);
                return null;
            }
        }
    }

}