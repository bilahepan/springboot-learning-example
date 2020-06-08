package demo.springboot.netty.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

    public static void main(String[] args) throws Exception {
        //bossGroup 处理连接请求；workerGroup 处理业务，二者都是无限循环的
        //bossGroup，workerGroup 包含的子线程数默认= 2*cpu核数
        //@io.netty.channel.MultithreadEventLoopGroup.DEFAULT_EVENT_LOOP_THREADS

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        //创建服务器端的启动对象，配置参数
        ServerBootstrap bootstrap = new ServerBootstrap();

        try {
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)//NioServerSocketChannel 作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128)//设置线程队列得到的连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        //给 pipeline 设置处理器
                        //pipeline 的 handler 是在真正大狐狸
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyServerHandler());//加入自己的处理器
                        }
                    });

            //绑定端口
            ChannelFuture cf = bootstrap.bind("127.0.0.1", 6668).sync();
            //ChannelFuture 可以注册一个监听器
            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        System.out.println("listener port 6668 success!");
                    } else {
                        System.out.println("listener port 6668 fail!");
                    }
                }
            });

            System.out.println("server is ready ok ...");
            //对关闭通道进行监听
            cf.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
