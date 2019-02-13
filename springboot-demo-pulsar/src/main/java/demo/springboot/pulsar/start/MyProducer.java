package demo.springboot.pulsar.start;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/1/2 下午4:14
 */

import org.apache.pulsar.client.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/1/2 下午4:16
 */
public class MyProducer {
    private static final Logger log = LoggerFactory.getLogger(MyProducer.class);
    private static final String SERVER_URL = "pulsar://localhost:6650";

    public static void main(String[] args) throws Exception {
        //构造Pulsar Client
        PulsarClient client = PulsarClient.builder()
                .serviceUrl(SERVER_URL)
                .build();
        // 构造生产者 生产者配置
        Producer<String> producer = client.newProducer(Schema.STRING)
                .producerName("my-producer")
                .topic("persistent://public/pulsar-cluster/default/my-topic")
                .create();
        // 同步发送消息
        MessageId messageId = producer.send("Hello World");
        log.info("message id is {}", messageId);


        // 异步消息发送
        CompletableFuture<MessageId> asyncMessageId = producer.sendAsync("This is a async message");
        // 阻塞线程，直到返回结果
        log.info("async message id is {}", asyncMessageId.get());

        // 配置发送的消息元信息，同步发送
        MessageId messageId2 = producer.newMessage()
                .key("my-message-key")
                .value("my-message")
                .property("my-key", "my-value")
                .property("my-other-key", "my-other-value")
                .send();
        log.info("messageId2 = {}", messageId2);

        // 配置发送的消息元信息，异步发送
        CompletableFuture<MessageId> asyncMessageId2 = producer.newMessage()
                .key("my-async-message-key")
                .value("my-async-message")
                .property("my-async-key", "my-async-value")
                .property("my-async-other-key", "my-async-other-value")
                .sendAsync();
        log.info("asyncMessageId2 = {}", asyncMessageId2);

        // 关闭producer的方式有两种：同步和异步
        // producer.closeAsync();
        producer.close();

        // 关闭licent的方式有两种，同步和异步
        // client.close();
        client.closeAsync();

        //producer 配置信息
        //http://pulsar.apache.org/api/client/org/apache/pulsar/client/api/ProducerBuilder
        /**
         *
         * ；{
         *   "topicName" : "persistent://public/pulsar-cluster/default/my-topic", //topicName 由四部分组成 [topic类型://租户名/命名空间/主题名]
         *   "producerName" : "my-producer",    //生产者名称
         *   "sendTimeoutMs" : 30000, //发送超时时间，默认 30s
         *   "blockIfQueueFull" : false, //消息队列已满时是否阻止发送操作 默认false,当消息队列满，发送操作将立即失败
         *   "maxPendingMessages" : 1000,//设置等待接收来自broker的确认消息的队列的最大大小,队列满试,blockIfQueueFull=true才有效
         *   "maxPendingMessagesAcrossPartitions" : 50000,//设置所有分区的最大挂起消息数
         *   "messageRoutingMode" : "CustomPartition", //消息分发路由模式  CustomPartition；RoundRobinPartition 环形遍历分区；SinglePartition 随机选择一个分区 // 参考 http://pulsar.apache.org/docs/zh-CN/2.2.0/cookbooks-partitioned/
         *   "hashingScheme" : "JavaStringHash",//更改用于选择在何处发布特定消息的分区的哈希方案
         *   "cryptoFailureAction" : "FAIL",//为失效的生产者指定一个默认的特定值
         *   "batchingMaxPublishDelayMicros" : 1000,//设置发送的消息将被成批处理的时间段默认值：如果启用了成批消息，则为1毫秒。
         *   "batchingMaxMessages" : 1000, //设置批处理中允许的最大消息数
         *   "batchingEnabled" : true, //控制是否为生产者启用消息的自动批处理。
         *   "compressionType" : "NONE", //设置生产者的压缩类型
         *   "initialSequenceId" : null, //为生产者发布的消息设置序列ID的基础值
         *   "properties" : { } //为生产者设置属性
         * }
         * */


        //client 配置信息
        //http://pulsar.apache.org/api/client/org/apache/pulsar/client/api/PulsarClient.html
        //http://pulsar.apache.org/api/client/org/apache/pulsar/client/api/ClientBuilder.html
        /**
         *
         * {
         *   "serviceUrl" : "pulsar://localhost:6650", //broker集群地址
         *   "operationTimeoutMs" : 30000, //操作超时设置
         *   "statsIntervalSeconds" : 60, //设置每个统计信息之间的间隔（默认值：60秒）统计信息将以正值激活状态间隔秒数应设置为至少1秒
         *   "numIoThreads" : 1,//设置用于处理与broker的连接的线程数（默认值：1个线程）
         *   "numListenerThreads" : 1,// 设置要用于消息侦听器的线程数（默认值：1个线程）
         *   "connectionsPerBroker" : 1, //设置客户端库将向单个broker打开的最大连接数。
         *   "useTcpNoDelay" : true, //配置是否在连接上使用延迟tcp,默认为true。无延迟功能确保数据包尽快发送到网络上，实现低延迟发布至关重要。另一方面，发送大量的小数据包可能会限制整体吞吐量。
         *   "useTls" : false, // 启用ssl,在serviceurl中使用“pulsar+ssl://”启用
         *   "tlsTrustCertsFilePath" : "",//设置受信任的TLS证书文件的路径
         *   "tlsAllowInsecureConnection" : false, //配置pulsar客户端是否接受来自broker的不受信任的TLS证书（默认值：false）
         *   "tlsHostnameVerificationEnable" : false,//它允许在客户端通过TLS连接到代理时验证主机名验证
         *   "concurrentLookupRequest" : 5000,//允许在每个broker连接上发送的并发查找请求数，以防止代理过载。
         *   "maxLookupRequest" : 50000,//为防止broker过载，每个broker连接上允许的最大查找请求数。
         *   "maxNumberOfRejectedRequestPerConnection" : 50,//设置在特定时间段（30秒）内被拒绝的broker请求的最大数目，在此时间段后，当前连接将关闭，客户端将创建一个新连接，以便有机会连接其他broker（默认值：50）
         *   "keepAliveIntervalSeconds" : 30 //为每个客户端broker连接设置以秒为单位的心跳检测时间
         * }
         *
         * */


        //自定义身份验证授权机制
        //http://pulsar.apache.org/docs/en/2.2.0/security-extending/

    }
}