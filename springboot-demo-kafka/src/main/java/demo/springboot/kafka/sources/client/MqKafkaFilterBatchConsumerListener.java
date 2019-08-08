package demo.springboot.kafka.sources.client;

import demo.springboot.kafka.sources.basic.domain.MqKafkaData;
import demo.springboot.kafka.sources.biz.IMqKafkaBatchConsumerConfigListener;
import demo.springboot.kafka.sources.biz.IMqKafkaRecordFilterStrategy;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.BatchMessageListener;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Iterator;
import java.util.List;

/**
 * 增加过滤的批量消费监听器
 *
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/8/6 下午9:06
 */
public class MqKafkaFilterBatchConsumerListener<T> implements BatchMessageListener<String, MqKafkaData<T>> {

    private IMqKafkaRecordFilterStrategy recordFilterStrategy;

    private IMqKafkaBatchConsumerConfigListener consumerConfigListener;

    public MqKafkaFilterBatchConsumerListener(IMqKafkaRecordFilterStrategy recordFilterStrategy, IMqKafkaBatchConsumerConfigListener consumerConfigListener) {
        this.recordFilterStrategy = recordFilterStrategy;
        this.consumerConfigListener = consumerConfigListener;
    }

    public IMqKafkaRecordFilterStrategy getRecordFilterStrategy() {
        return recordFilterStrategy;
    }

    public IMqKafkaBatchConsumerConfigListener getConsumerConfigListener() {
        return consumerConfigListener;
    }


    @Override
    public void onMessage(List<ConsumerRecord<String, MqKafkaData<T>>> consumerRecords) {
        filter(consumerRecords);
        if (consumerRecords.size() > 0) {
            consumerConfigListener.onMessage(consumerRecords);
        }
    }


    //TODO
    private void filter(List<ConsumerRecord<String, MqKafkaData<T>>> consumerRecords) {
        Assert.notNull(recordFilterStrategy, "过滤器策略不为null.");
        Assert.notNull(consumerConfigListener, "消费监听器不为null.");

        if (CollectionUtils.isEmpty(consumerRecords)) {
            return;
        }
        Iterator<ConsumerRecord<String, MqKafkaData<T>>> iterator = consumerRecords.iterator();
        while (iterator.hasNext()) {
            //java中的集合边遍历边删除是需要使用迭代器中的 remove() 方法
            if (!recordFilterStrategy.filter(iterator.next())) {
                iterator.remove();
            }
        }
    }
}