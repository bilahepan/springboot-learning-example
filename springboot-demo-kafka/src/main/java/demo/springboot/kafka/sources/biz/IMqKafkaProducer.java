package demo.springboot.kafka.sources.biz;


import com.google.common.hash.Hashing;
import demo.springboot.kafka.sources.basic.domain.MqKafkaData;
import demo.springboot.kafka.sources.basic.domain.MqKafkaSendResult;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.Future;

/**
 * Created by kedumin on 16/7/26.
 */
public interface IMqKafkaProducer<T> {

    MqKafkaSendResult send(String topic, MqKafkaData<T> value);

    MqKafkaSendResult send(String topic, MqKafkaData<T> value, String key);

    //同步生产数据接口-指定分区
    MqKafkaSendResult send(String topic, MqKafkaData<T> value, String key, Integer partition);

    /**
     * kafka同步生产数据接口，指定关键字来自动路由分区(String 类型)
     * 使用MurmurHash3计算32-bits哈希值 {@link Hashing#murmur3_32()};有效改善分区数据倾斜问题
     *
     * @param topic
     * @param value
     * @param key
     * @param partitions 分区总数
     * @param keyWord    根据该参数定位分区，String 类型
     * @return
     */
    MqKafkaSendResult send(String topic, MqKafkaData<T> value, String key, Integer partitions, String keyWord);


    //kafka同步生产数据接口，指定关键字来自动路由分区(Integer 类型)
    MqKafkaSendResult send(String topic, MqKafkaData<T> value, String key, Integer partitions, Integer keyWord);

    /**
     * 异步发送消息接口，返回Future，允许调用方根据结果自行处理
     *
     * @param topic
     * @param value
     * @param key
     * @param partitions 分区个数，必须大于0
     * @param keyWord    根据该参数计算哈希值，定位分区，String类型
     * @return
     */
    Future<SendResult<String, MqKafkaData<T>>> asyncSend(String topic, MqKafkaData<T> value, String key, Integer partitions, String keyWord);

    //kafka异步生产数据接口
    Future<SendResult<String, MqKafkaData<T>>> asyncSend(String topic, MqKafkaData<T> value, String key, Integer partition);


    MqKafkaSendResult syncSend(String topic, MqKafkaData<T> value);


    MqKafkaSendResult syncSend(String topic, MqKafkaData<T> value, String key);


    /**
     * kafka异步生产数据接口
     * 使用MurmurHash3计算32-bits哈希值 {@link Hashing#murmur3_32()}; 有效改善分区数据倾斜问题
     *
     * @param topic
     * @param value
     * @param key
     * @param partitions
     * @param keyWord    根据该参数定位分区, String 类型
     * @return
     */
    MqKafkaSendResult syncSend(String topic, MqKafkaData<T> value, String key, Integer partitions, String keyWord);


    MqKafkaSendResult syncSend(String topic, MqKafkaData<T> value, String key, Integer partitions, Integer keyWord);

}

