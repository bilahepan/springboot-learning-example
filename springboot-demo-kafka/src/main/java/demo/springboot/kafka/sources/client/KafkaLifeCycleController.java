package demo.springboot.kafka.sources.client;

import com.alibaba.fastjson.JSON;
import demo.springboot.kafka.sources.basic.domain.MqKafkaBatchConsumerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Tuya.com Inc.
 * <p>kafka消费端生命周期控制
 * <p>在应用下线前实现kafka优雅下线，避免
 * <li>1. kafka业务逻辑依赖的其他服务先下线导致kafka消费失败</li>
 * <li>2. 应用下线前未提交offset，若当前机器重新上线前对应的分区发生Rebalance，导致这些分区被重复消费</li>
 * <p>
 * 后续可以将应用的其他服务优雅下线也放到这里进行控制，如dubbo
 *
 * <a href="https://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/HttpServer.html" />
 */
public class KafkaLifeCycleController {

    private static Logger logger = LoggerFactory.getLogger(KafkaLifeCycleController.class);

    /* 初始化两个set置入方法区 */
    private static final Set<MqKafkaConsumer> singleConsumerContexts = new HashSet<>();
    private static final Set<MqKafkaBatchConsumerContext> batchConsumerContexts = new HashSet<>();

    private static final Set<KafkaConsumerContext> newConsumerContexts = new HashSet<>();

    // 等待时间 毫秒
    private final static long DEFAULT_WAIT_TIME = 2000;
    private static long waitTime = DEFAULT_WAIT_TIME;

    public static void addSingleConsumerContext(MqKafkaConsumer singleConsumerContext) {
        if (!Objects.isNull(singleConsumerContext)) {
            singleConsumerContexts.add(singleConsumerContext);
        }
    }

    public static void addBatchConsumerContext(MqKafkaBatchConsumerContext batchConsumerContext) {
        if (!Objects.isNull(batchConsumerContext)) {
            batchConsumerContexts.add(batchConsumerContext);
        }
    }

    public static void addNewConsumerContext(KafkaConsumerContext consumerContext) {
        if (!Objects.isNull(consumerContext)) {
            newConsumerContexts.add(consumerContext);
        }
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime <= 0 ? DEFAULT_WAIT_TIME : waitTime;
    }

    public static String stopConsumer() {
        logger.info("接收到kafka stop consumer指令");
        return doStop();
    }

    /**
     * kafka关闭消费，提交offset，取消订阅
     *
     * @see AbstractMessageListenerContainer#stop()
     * @see KafkaMessageListenerContainer#doStop(Runnable)
     * @see KafkaMessageListenerContainer.ListenerConsumer#run() 判断{@link AbstractMessageListenerContainer#running}，当
     * 为<code>false</code>时，提交offset，取消订阅
     */
    private static String doStop() {
        String flag = "success";
        logger.info("共有{}个单条消费的context、{}个批量消费的context、{}个手动分配分区的context需要stop",
                singleConsumerContexts.size(), batchConsumerContexts.size(), newConsumerContexts.size());
        for (MqKafkaConsumer consumer : singleConsumerContexts) {
            try {
                if (!Objects.isNull(consumer)) {
                    consumer.stop();
                }
            } catch (Exception e) {
                logger.error("关闭kafka单条消费失败。MqKafkaConsumer={}", JSON.toJSONString(consumer), e);
                flag = "failure";
            }
        }

        for (KafkaBatchConsumerContext consumer : batchConsumerContexts) {
            try {
                if (!Objects.isNull(consumer)) {
                    consumer.stop();
                }
            } catch (Exception e) {
                logger.error("关闭kafka批量消费失败。KafkaBatchConsumerContext={}", JSON.toJSONString(consumer), e);
                flag = "failure";
            }
        }

        for (KafkaConsumerContext consumerContext : newConsumerContexts) {
            try {
                if (!Objects.isNull(consumerContext)) {
                    consumerContext.close();
                }
            } catch (Exception e) {
                logger.error("关闭手动分配kafka分区的消费context失败。KafkaConsumerContext={}", consumerContext, e);
                flag = "failure";
            }
        }

        try {
            // 暂时无法判断已经被调用的那批数据是否完成，等待一会。
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            // nothing to do
        }

        return flag;
    }

}
