package demo.springboot.kafka.sources.biz;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/8/6 下午7:47
 */
public interface IMqKafkaConfigService<C> {

    /**
     * 设置配置服务
     */
    void setConfigServer(C configServer);

    /**
     * 获取配置服务
     */
    C getConfigServer();
}