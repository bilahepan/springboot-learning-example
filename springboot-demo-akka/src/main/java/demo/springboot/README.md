
## 对消息模型的理解看这篇文章
```text
AKKA浅析及在蘑菇街广告系统中的应用
https://mp.weixin.qq.com/s/GerKcod33EyIbu-J3BqwEQ
```

## Akka 简单性能测试
```text
Akka简单的性能测试
https://www.jianshu.com/p/6d62256e3327
```

## Akka的五大特性
1. 易于构建并行和分布式应用
2. 可靠性（Resilient by Design）
    系统具备自愈能力，在本地/远程都有监护。
3. 高性能（High Performance）
    在单机中每秒可发送50000000个消息。内存占用小，1GB内存中可保存2500000个actors。
4. 弹性，无中心（Elastic — Decentralized）
    自适应的负责均衡，路由，分区，配置。
5. 可扩展（Extensible） 
    可以使用Akka 扩展包进行扩展。


## Akka 的主要功能模块
https://doc.akka.io/docs/akka/current/guide/modules.html#persistence
1. Actor library
    > Actor 模型核心库
    ```
    和设计高性能的并发应用程序。
    如何在多线程环境中处理错误。
    如何保护我的项目免受并发性的困扰。
    ```
2. Remoting
    > 远程处理，使位于不同计算机上的Actor能够无缝地交换消息
    > Remoting解决了与远程系统组件进行寻址和通信的问题
    ```
    如何处理在远程主机上的actor系统。
    如何处理远程主机actor系统上的各个Actor
    如何将消息转换为线路上的字节
    如何透明地管理主机之间的低网络连接（和重新连接），检测崩溃的actor系统和主机。
    如何透明地在同一网络连接上复用来自不相关的一组Actor的通信。
    ```
3. Cluster
    > Actor系统集群处理
    ```
    如何维护一组可以相互通信并将彼此视为集群的一部分的actor系统（集群）。
    如何安全地将新系统引入现有成员集。
    如何可靠地检测暂时无法访问的系统。
    如何删除失败的主机/系统（或缩小系统），以便所有剩余成员就群集的剩余子集达成一致。
    如何在当前成员集之间分配计算。
    如何将群集成员指定为某个角色，换句话说，提供某些服务而不是其他服务。

    ```
4. Cluster Sharding
     > 集群分片
    ```
    如何在一组系统上建模和扩展大量有状态实体。
    如何确保群集中的实体正确分布，以便在计算机之间正确平衡负载。
    如何确保从崩溃的系统迁移实体而不会丢失状态。
    如何确保实体不会同时存在于多个系统上，从而保持一致。
    ```
5. Cluster Singleton
    > 集群单例
    ```
    如何确保整个群集中只运行一个服务实例。
    即使托管它的系统当前在缩小过程中崩溃或关闭，如何确保服务已启动。
    如何从群集的任何成员到达此实例，假设它可以随时间迁移到其他系统。
    ```

6. Cluster Publish-Subscribe
    > 群集发布 - 订阅
    ```
    如何向群集中感兴趣的一方广播消息。
    如何从群集中感兴趣的一方向成员发送消息。
    如何订阅和取消订阅群集中某个主题的事件
    ```

7. Persistence
    > 持久化
    ```
   如何在系统重新启动或崩溃时恢复实体/ actor的状态。
   如何实施CQRS系统(CQRS:命令查询职责分离(CQRS)模式)。
   如何确保在网络错误和系统崩溃时可靠地传递消息。
   如何内省导致实体进入其当前状态的域事件。
   如何利用应用程序中的事件源来支持在项目不断发展的同时支持长时间运行的流程。
    ```
8. Distributed Data
    > 分布式数据
    ```
    面对集群分区如何接受写入。
    如何在共享数据的同时确保低延迟的本地读写访问。
    ```
9. Streams
    > 流式处理
    ```
   如何处理具有高性能的事件流或大型数据集，利用并发性并保持资源使用紧密。
   如何将可重用的事件/数据处理组件组装成灵活的管道。
   如何以灵活的方式将异步服务相互连接，并具有良好的性能。
   如何提供或使用符合Reactive Streams标准的接口与第三方库进行交互。
    ```
10. HTTP
    > Http模块
    ```
    如何通过HTTP API以高效的方式将系统或集群的服务公开给外部世界。
    如何使用HTTP将大型数据集传入和传出系统。
    如何使用HTTP将实时事件传入和传出系统。
    ```