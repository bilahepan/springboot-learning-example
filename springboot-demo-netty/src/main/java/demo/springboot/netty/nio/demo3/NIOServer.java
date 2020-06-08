package demo.springboot.netty.nio.demo3;


import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
    public static void main(String[] args) throws Exception {
        //ServerSocketChannel 获取 channel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //非阻塞模式
        serverSocketChannel.configureBlocking(false);
        //绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress("127.0.0.1", 6666));

        //获取 selector
        Selector selector = Selector.open();

        //把 serverSocketChannel 注册到 selector 上，关心事件为 OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //循环等待客户端的连接
        while (true) {
            //等待1秒，没有事件发生返回
            if (selector.select(1000) == 0) {
                System.out.println("waiting 1s.");
                continue;
            }
            //如果返回大于0，获取到相关的 selectionKey
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                //根据key对应发生的事件，做对应的操作
                if (key.isAcceptable())
                //OP_ACCEPT 事件
                {
                    //该客户端生成一个 socketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //socketChannel 设置为非阻塞的
                    socketChannel.configureBlocking(false);
                    //客户端连接成功，生成 socketChannel
                    System.out.println("connect ok, socketChannel hashCode=" + socketChannel.hashCode());
                    //将 socketChannel 注册到 selector，关注事件为 OP_READ
                    //同时给 socketChannel 关联一个 buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if (key.isReadable()) {
                    //通过 key 反向获取 channel
                    SocketChannel channel = (SocketChannel) key.channel();
                    //获取 channel 关联的 buffer
                    ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
                    channel.read(byteBuffer);
                    System.out.println("从客户端收到=" + new String(byteBuffer.array()));
                }
                //手动从集合中移除 selectionKey ，防止重复操作
                keyIterator.remove();
            }
        }
    }
}
