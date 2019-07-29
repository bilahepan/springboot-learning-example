package demo.springboot.kafka.sources.basic.domain;

import demo.springboot.kafka.commons.NoNullFieldStringStyle;
import demo.springboot.kafka.sources.biz.IMqKafkaBizConsumer;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/7/25 下午8:49
 */
public class MqKafkaConsumerConfig {

    private String topic;

    private List<MqKafkaPartitions> partitions;

    private String key;

    private IMqKafkaBizConsumer consumer;//消费者

    private Boolean ignore = false;//不管异常，直接跳过，不重试

    private Integer tryNum = 3;//重试次数

    private Integer works = 6;//执行线程数

    private String beanName;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<MqKafkaPartitions> getPartitions() {
        return partitions;
    }

    public void setPartitions(List<MqKafkaPartitions> partitions) {
        this.partitions = partitions;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public IMqKafkaBizConsumer getConsumer() {
        return consumer;
    }

    public void setConsumer(IMqKafkaBizConsumer consumer) {
        this.consumer = consumer;
    }

    public Boolean getIgnore() {
        return ignore;
    }

    public void setIgnore(Boolean ignore) {
        this.ignore = ignore;
    }

    public Integer getTryNum() {
        return tryNum;
    }

    public void setTryNum(Integer tryNum) {
        this.tryNum = tryNum;
    }

    public Integer getWorks() {
        return works;
    }

    public void setWorks(Integer works) {
        this.works = works;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new NoNullFieldStringStyle());
    }
}