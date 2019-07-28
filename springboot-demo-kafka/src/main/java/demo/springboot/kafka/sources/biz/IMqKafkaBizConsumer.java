package demo.springboot.kafka.sources.biz;

import demo.springboot.kafka.sources.basic.domain.MqKafkaData;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/7/25 下午8:52
 */
public interface IMqKafkaBizConsumer<T> {


    /**
     * 消费成功返回true
     * 失败返回false
     */
    public boolean consumer(String topic, MqKafkaData<T> data, int partition, long offset, String key) throws Exception;


    /**
     * 获取重试次数
     */
    public int getRetryTimes();


}