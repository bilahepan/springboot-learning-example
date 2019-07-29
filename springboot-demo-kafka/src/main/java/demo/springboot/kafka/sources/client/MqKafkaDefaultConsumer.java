package demo.springboot.kafka.sources.client;

import demo.springboot.kafka.sources.basic.domain.MqKafkaData;
import demo.springboot.kafka.sources.biz.IMqKafkaConsumer;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.Acknowledgment;

import java.util.Objects;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/7/30 上午7:46
 */
public class MqKafkaDefaultConsumer<T> extends MqKafkaBaseConsumer<T>
        implements IMqKafkaConsumer<String, MqKafkaData<T>>, MessageListener<String, MqKafkaData<T>> {

    protected static Logger logger = LoggerFactory.getLogger(MqKafkaDefaultConsumer.class);

    /**
     * kafka消费组
     */
    private static String GROUP_ID = "group.id";

    public void onMessage(ConsumerRecord<String, MqKafkaData<T>> record, Acknowledgment acknowledgment) {
        logger.error("不应该调用这里的方法");
    }

    @Override
    public void onMessage(ConsumerRecord<String, MqKafkaData<T>> record) {
        try {
            //重复消费时不是相同消费组的失败消息会被过滤掉,防止重复消费
            if (filter(record)) {
                //调动父类的消费方法
                doMessage(record);
            }
            return;
            //抛异常
        } catch (RuntimeException e) {
            if (record != null) {
                logger.error(record.toString());
            } else {
                logger.error("record==null");
            }
            logger.error("", e);
            throw new RuntimeException(e.getMessage(), e);
        } catch (Exception e) {
            if (record != null) {
                logger.error(record.toString());
            } else {
                logger.error("record==null");
            }
            logger.error("", e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * {@link MqKafkaErrorHandler}会将消费失败的需要重试的数据重新扔入相同topic，导致订阅该topic的所有消费组重复消费这条记录。
     * 重复消费时不是相同消费组的失败消息会被过滤掉。
     * 在如下情况消息不会被过滤掉，可以消费：
     * <ul>
     * <li> 新来消息即{@link MqKafkaData#count}为0表示未被任何消费组消费；
     * <li> 被重试消息和当前消费组一致；
     * </ul>
     *
     * @param record
     * @return
     * @see MqKafkaErrorHandler
     */
    private boolean filter(ConsumerRecord<String, MqKafkaData<T>> record) {
        MqKafkaData<T> value = record.value();
        if (value.getCount() == 0) {
            return true;
        } else {
            Object groupId = getNativeConsumerValue(GROUP_ID);
            if (Objects.isNull(groupId)) {
                logger.error("找不到group.id, 不会重试消费，请检查kafka原生消费配置. record={}", record);
                return false;
            }
            if (StringUtils.equals((String) groupId, value.getGroupId())) {
                return true;
            }
        }
        return false;
    }

}