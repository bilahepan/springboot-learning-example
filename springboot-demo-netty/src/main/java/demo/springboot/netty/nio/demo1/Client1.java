package demo.springboot.netty.nio.demo1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Scanner;

/**
 * @author: bilahepan
 * @date: 2019/5/19 上午12:13
 */
public class Client1 {

    public static void main(String[] args) throws IOException {
        System.out.println("客户端已经启动....");
        // 1.创建通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8080));
        // 2.切换异步非阻塞
        socketChannel.configureBlocking(false);
        // 3.指定缓冲区大小
        //  ByteBuffer 用法参考 https://www.cnblogs.com/ruber/p/6857159.html
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String str = scanner.next();
            byteBuffer.put((new Date().toString() + "\n" + str).getBytes());
            // 4.切换读取模式
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
            byteBuffer.clear();
        }
        socketChannel.close();
    }
}