package demo.springboot.kafka.sources.biz.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/8/6 下午1:09
 */
public class KafkaUtil {
    /**
     * 获取kafka原生配置信息
     *
     * @return 返回不可修改Map
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @see DefaultKafkaConsumerFactory#configs
     */
    public static Map<String, Object> getNativeConsumerConfigs(DefaultKafkaConsumerFactory consumerFactory, String field) throws NoSuchFieldException, IllegalAccessException {
        if (Objects.isNull(consumerFactory) || StringUtils.isBlank(field)) {
            return null;
        }
        //TODO 该写法学习下
        Field configs = DefaultKafkaConsumerFactory.class.getDeclaredField(field);
        configs.setAccessible(true);
        Map<String, Object> objectMap = (Map<String, Object>) configs.get(consumerFactory);
        Assert.notEmpty(objectMap);
        return Collections.unmodifiableMap(objectMap);
    }


}
