package demo.springboot.sentinel.cluster;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 *
 *@author: 文若[gaotc@tuya.com]
 *@date: 2019/6/2 下午2:27
 */
@Configuration
public class AopConfig {

    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }
}