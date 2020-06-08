package demo.springboot.netty.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 客户端处理器
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * 当通道就绪，就会触发该方法
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //super.channelActive(ctx);
        String backMsg = "hello server, connected ok!";
        ctx.writeAndFlush(Unpooled.copiedBuffer(backMsg, CharsetUtil.UTF_8));
    }

    /**
     * 当有读取事件时，会触发
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //super.channelRead(ctx, msg);
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("server back msg :" + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("server remote address:" + ctx.channel().remoteAddress());
    }

    /**
     * 发生异常时
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }
}
