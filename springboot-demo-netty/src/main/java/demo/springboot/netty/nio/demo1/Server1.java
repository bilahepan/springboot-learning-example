package demo.springboot.netty.nio.demo1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author: bilahepan
 * @date: 2019/5/19 上午12:15
 */
public class Server1 {
    public static void main(String[] args) throws IOException {
        System.out.println("服务器端已经启动....");
        // 1.创建通道
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        // 2.切换读取模式
        serverSocket.configureBlocking(false);
        // 3.绑定连接
        serverSocket.bind(new InetSocketAddress(8080));
        // 4.获取选择器
        Selector selector = Selector.open();
        // 5.将通道注册到选择器 "并且指定监听接收事件"
        // 四种类型用来表示四种不同类型的事件：可读、可写、可连接、可接受连接
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        // 6. 轮训式 获取选择 "已经准备就绪"的事件
        while (selector.select() > 0) {
            // 7.获取当前选择器所有注册的"已经就绪的监听事件"
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                // 8.获取准备就绪的事件
                SelectionKey sk = it.next();
                // 9.判断具体是什么事件准备就绪
                if (sk.isAcceptable()) {
                    // 10.若"接受就绪",获取客户端连接
                    SocketChannel socketChannel = serverSocket.accept();
                    // 11.设置阻塞模式
                    socketChannel.configureBlocking(false);
                    // 12.将该通道注册到服务器上
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (sk.isReadable()) {
                    // 13.获取当前选择器"就绪" 状态的通道
                    SocketChannel socketChannel = (SocketChannel) sk.channel();
                    // 14.读取数据
                    ByteBuffer buf = ByteBuffer.allocate(1024);
                    int len = 0;
                    while ((len = socketChannel.read(buf)) > 0) {
                        buf.flip();
                        System.out.println(new String(buf.array(), 0, len));
                        buf.clear();
                    }
                }
                it.remove();
            }
        }

    }
}