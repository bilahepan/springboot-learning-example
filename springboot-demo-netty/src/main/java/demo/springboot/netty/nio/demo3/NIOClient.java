package demo.springboot.netty.nio.demo3;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient {

    public static void main(String[] args) throws Exception {
        //得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        //设置非阻塞
        socketChannel.configureBlocking(false);
        //提供服务器ip，端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
        //连接服务器
        //没有连接上，可以去做其他事情
        if (!socketChannel.connect(inetSocketAddress)) {
            //连接是需要时间的，客户端不会阻塞，客户端可以去做其他事情
            while (!socketChannel.finishConnect()) {
                System.out.println("客户端可以去做其他事情...");
            }
        }
        //连接上了，发送数据
        String str = "hello nio demo!";
        socketChannel.write(ByteBuffer.wrap(str.getBytes()));
        socketChannel.close();
    }
}
