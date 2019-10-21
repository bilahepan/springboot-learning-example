package demo.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 *@author: bilahepan
 *@date: 2018/12/2 下午7:55
 */
@SpringBootApplication//@SpringBootApplication：Spring Boot 应用的标识
public class QuickStartApplication {
    public static void main(String[] args) {
        //SpringApplication引导应用，并将Application本身作为参数传递给run方法。具体run方法会启动嵌入式的Tomcat并初始化Spring环境及其各Spring组件。
        SpringApplication.run(QuickStartApplication.class, args);
    }
}
