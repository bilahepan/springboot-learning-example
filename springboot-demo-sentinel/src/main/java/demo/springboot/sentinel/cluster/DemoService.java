package demo.springboot.sentinel.cluster;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Service;

/**
 *
 *@author: 文若[gaotc@tuya.com]
 *@date: 2019/6/2 下午2:30
 */
@Service
public class DemoService {

    // 原本的业务方法.
    @SentinelResource(blockHandler = "sayHelloBlockHandler")
    public String sayHello(String name) {
        return "Hello, " + name;
    }

    // blockHandler 函数，原方法调用被限流/降级/系统保护的时候调用
    public String sayHelloBlockHandler(String name, BlockException ex) {
        // This is the block handler.
        ex.printStackTrace();
        return String.format("Oops, <%s> blocked by Sentinel", name);
    }
}