package demo.springboot.netty.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 自定义的 Handler 继承 ChannelHandlerAdapter
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取数据（我们用来读取客户端发送的数据）
     * ChannelHandlerContext 包含 pipeline，通道 channel, 地址等
     * Object msg 就是客户端发送的数据
     *
     * ----------------------------
     * channel , ctx ,pipeline 的关系
     *
     * （1）pipeline 是一个双向链表结构，通过 pipeline 可以拿到 channel;
     * （2）channel 也可以拿到 pipeline , 二者是相互包含的关系
     * （3）ctx 包含所有，ctx 还包含其他很多信息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //super.channelRead(ctx, msg);
        //ByteBuf 是 netty 提供的；ByteBuffer 是 NIO 自己的
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("threadName=" + Thread.currentThread().getName());
        System.out.println("receive msg from client, msg=" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("remote adress=" + ctx.channel().remoteAddress());
        //
    }

    /**
     * 读取数据完毕后，进行的处理
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //super.channelReadComplete(ctx);
        //writeAndFlush 数据写入到缓冲，并刷新
        String backMsg = "hello client!";
        ctx.writeAndFlush(Unpooled.copiedBuffer(backMsg, CharsetUtil.UTF_8));
    }

    /**
     * 处理异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //super.exceptionCaught(ctx, cause);
        ctx.channel().close();
    }
}
