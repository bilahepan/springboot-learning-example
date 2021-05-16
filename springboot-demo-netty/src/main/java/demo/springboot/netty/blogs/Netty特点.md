
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


## Bootstrap & ServerBootstrap

    Bootstrap 用于启动一个 Netty TCP 客户端，或者 UDP 的一端。

    通常使用 #connet(...) 方法连接到远程的主机和端口，作为一个 Netty TCP 客户端。
    也可以通过 #bind(...) 方法绑定本地的一个端口，作为 UDP 的一端。
    仅仅需要使用一个 EventLoopGroup 。
    ServerBootstrap 往往是用于启动一个 Netty 服务端。

    通常使用 #bind(...) 方法绑定本地的端口上，然后等待客户端的连接。
    使用两个 EventLoopGroup 对象( 当然这个对象可以引用同一个对象 )：第一个用于处理它本地 Socket 连接的 IO 事件处理，而第二个责负责处理远程客户端的 IO 事件处理。


### Channel

   Channel 是 Netty 网络操作抽象类，它除了包括基本的 I/O 操作，如 bind、connect、read、write 之外，还包括了 Netty 框架相关的一些功能，如获取该 Channel 的 EventLoop 。

   在传统的网络编程中，作为核心类的 Socket ，它对程序员来说并不是那么友好，直接使用其成本还是稍微高了点。
   而 Netty 的 Channel 则提供的一系列的 API ，它大大降低了直接与 Socket 进行操作的复杂性。
   而相对于原生 NIO 的 Channel，Netty 的 Channel 具有如下优势( 摘自《Netty权威指南( 第二版 )》) ：

   在 Channel 接口层，采用 Facade 模式进行统一封装，将网络 I/O 操作、网络 I/O 相关联的其他操作封装起来，统一对外提供。
   Channel 接口的定义尽量大而全，为 SocketChannel 和 ServerSocketChannel 提供统一的视图，由不同子类实现不同的功能，公共功能在抽象父类中实现，最大程度地实现功能和接口的重用。
   具体实现采用聚合而非包含的方式，将相关的功能类聚合在 Channel 中，由 Channel 统一负责和调度，功能实现更加灵活。


 ## EventLoop && EventLoopGroup
    Netty 基于事件驱动模型，使用不同的事件来通知我们状态的改变或者操作状态的改变。它定义了在整个连接的生命周期里当有事件发生的时候处理的核心抽象。

    Channel 为Netty 网络操作抽象类，EventLoop 负责处理注册到其上的 Channel 处理 I/O 操作，两者配合参与 I/O 操作。

    EventLoopGroup 是一个 EventLoop 的分组，它可以获取到一个或者多个 EventLoop 对象，因此它提供了迭代出 EventLoop 对象的方法。

   一个 EventLoopGroup 包含一个或多个 EventLoop ，即 EventLoopGroup : EventLoop = 1 : n 。
   一个 EventLoop 在它的生命周期内，只能与一个 Thread 绑定，即 EventLoop : Thread = 1 : 1 。
   所有有 EventLoop 处理的 I/O 事件都将在它专有的 Thread 上被处理，从而保证线程安全，即 Thread : EventLoop = 1 : 1。
   一个 Channel 在它的生命周期内只能注册到一个 EventLoop 上，即 Channel : EventLoop = n : 1 。
   一个 EventLoop 可被分配至一个或多个 Channel ，即 EventLoop : Channel = 1 : n 。
   当一个连接到达时，Netty 就会创建一个 Channel，然后从 EventLoopGroup 中分配一个 EventLoop 来给这个 Channel 绑定上，在该 Channel 的整个生命周期中都是有这个绑定的 EventLoop 来服务的。


 ## ChannelFuture
     Netty 为异步非阻塞，即所有的 I/O 操作都为异步的，因此，我们不能立刻得知消息是否已经被处理了。
     Netty 提供了 ChannelFuture 接口，通过该接口的 #addListener(...) 方法，
     注册一个 ChannelFutureListener，当操作执行成功或者失败时，监听就会自动触发返回结果。


## ChannelHandler，ChannelInboundHandler，ChannelOutboundHandler
    ChannelHandler ，连接通道处理器，我们使用 Netty 中最常用的组件。ChannelHandler 主要用来处理各种事件，这里的事件很广泛，比如可以是连接、数据接收、异常、数据转换等。

    ChannelHandler 有两个核心子类 ChannelInboundHandler 和 ChannelOutboundHandler，其中 ChannelInboundHandler 用于接收、处理入站( Inbound )的数据和事件，而 ChannelOutboundHandler 则相反，用于接收、处理出站( Outbound )的数据和事件。

    ChannelInboundHandler 的实现类还包括一系列的 Decoder 类，对输入字节流进行解码。
    ChannelOutboundHandler 的实现类还包括一系列的 Encoder 类，对输入字节流进行编码。
    ChannelDuplexHandler 可以同时用于接收、处理入站和出站的数据和时间。

    ChannelHandler 还有其它的一系列的抽象实现 Adapter ，以及一些用于编解码具体协议的 ChannelHandler 实现类。


## ChannelPipeline
 ChannelPipeline 为 ChannelHandler 的链，提供了一个容器并定义了用于沿着链传播入站和出站事件流的 API 。
 一个数据或者事件可能会被多个 Handler 处理，在这个过程中，数据或者事件经流 ChannelPipeline ，由 ChannelHandler 处理。
 在这个处理过程中，一个 ChannelHandler 接收数据后处理完成后交给下一个 ChannelHandler，或者什么都不做直接交给下一个 ChannelHandler。

 当一个数据流进入 ChannelPipeline 时，它会从 ChannelPipeline 头部开始，传给第一个 ChannelInboundHandler 。当第一个处理完后再传给下一个，一直传递到管道的尾部。
 与之相对应的是，当数据被写出时，它会从管道的尾部开始，先经过管道尾部的“最后”一个ChannelOutboundHandler ，当它处理完成后会传递给前一个 ChannelOutboundHandler 。


 当 ChannelHandler 被添加到 ChannelPipeline 时，它将会被分配一个 ChannelHandlerContext ，它代表了 ChannelHandler 和 ChannelPipeline 之间的绑定。其中 ChannelHandler 添加到 ChannelPipeline 中，通过 ChannelInitializer 来实现，过程如下：

 一个 ChannelInitializer 的实现对象，被设置到了 BootStrap 或 ServerBootStrap 中。
 当 ChannelInitializer#initChannel() 方法被调用时，ChannelInitializer 将在 ChannelPipeline 中创建一组自定义的 ChannelHandler 对象。
 ChannelInitializer 将它自己从 ChannelPipeline 中移除。
