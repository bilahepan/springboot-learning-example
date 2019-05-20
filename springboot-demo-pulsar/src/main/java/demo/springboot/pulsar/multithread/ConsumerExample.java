
package demo.springboot.pulsar.multithread;

import org.apache.pulsar.client.api.Consumer;

import java.util.function.BiConsumer;

/**
 *
 *@author: bilahepan
 *@date: 2019/4/28 下午9:27
 */
public class ConsumerExample {
    public static void main(String[] args) throws Exception {
        PulsarConsumerPoolFactory.getConsumerPool().forEach(new BiConsumer<Integer, Consumer>() {
            @Override
            public void accept(Integer consumerNum, Consumer consumer) {
                ThreadPoolFactory.getCustomThreadPool().submit(new MessageHandlerTask(consumerNum, consumer));
            }
        });
    }
}
