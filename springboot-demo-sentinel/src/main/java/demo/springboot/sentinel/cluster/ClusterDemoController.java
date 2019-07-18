package demo.springboot.sentinel.cluster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 *@author: 文若[gaotc@tuya.com]
 *@date: 2019/6/2 下午2:30
 */
@RestController
public class ClusterDemoController {

    @Autowired
    private DemoService service;

    @GetMapping("/hello/{name}")
    public String apiHello(@PathVariable String name) throws Exception {
        return service.sayHello(name);
    }
}
