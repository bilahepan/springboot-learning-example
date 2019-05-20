
## 
    Netty 是一个基于 JAVA NIO 类库的异步通信框架，它的架构特点是：异步非阻塞、基于事件驱动、高性能、高可靠性和高可定制性。

## Netty应用场景

    1.分布式开源框架中dubbo、Zookeeper、RocketMQ底层rpc通讯使用的就是 Netty 。
    2.游戏开发中，底层使用 Netty 通讯。

## 为什么选择netty

1) NIO的类库和API繁杂，使用麻烦，你需要熟练掌握Selector、ServerSocketChannel、SocketChannel、ByteBuffer等；
2) 需要具备其它的额外技能做铺垫，例如熟悉Java多线程编程，因为NIO编程涉及到Reactor模式，你必须对多线程和网路编程非常熟悉，才能编写出高质量的NIO程序；
3) 可靠性能力补齐，工作量和难度都非常大。例如客户端面临断连重连、网络闪断、半包读写、失败缓存、网络拥塞和异常码流的处理等等，NIO编程的特点是功能开发相对容易，但是可靠性能力补齐工作量和难度都非常大；
4) JDK NIO的BUG，例如臭名昭著的epoll bug，它会导致Selector空轮询，最终导致CPU 100%。官方声称在JDK1.6版本的update18修复了该问题，但是直到JDK1.7版本该问题仍旧存在，只不过该bug发生概率降低了一些而已，它并没有被根本解决。



使用 jdk 自带的 nio 需要了解太多的概念，编程复杂
netty 底层 IO 模型随意切换，而这一切只需要做微小的改动
netty 自带的拆包解包，异常检测等机制让你从nio的繁重细节中脱离出来，让你只需要关心业务逻辑
netty 解决了 jdk 的很多包括空轮训在内的 bug
netty 底层对线程，selector 做了很多细小的优化，精心设计的 reactor 线程做到非常高效的并发处理
自带各种协议栈让你处理任何一种通用协议都几乎不用亲自动手
netty 社区活跃，遇到问题随时邮件列表或者 issue
netty 已经历各大rpc框架，消息中间件，分布式通信中间件线上的广泛验证，健壮性无比强大



## Netty 有如下几个核心组件：

Bootstrap & ServerBootstrap
Channel
ChannelFuture
EventLoop & EventLoopGroup
ChannelHandler
ChannelPipeline

## Netty 各个组件介绍

http://svip.iocoder.cn/Netty/intro-2/
