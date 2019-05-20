


1 Channel 
http://svip.iocoder.cn/Netty/nio-2-channel/
2 Buffer
http://svip.iocoder.cn/Netty/nio-3-buffer/
3 Selector
http://svip.iocoder.cn/Netty/nio-4-selector/


Channel 有非常多的实现类，最为重要的四个 Channel 实现类如下：

SocketChannel ：一个客户端用来发起 TCP 的 Channel 。
ServerSocketChannel ：一个服务端用来监听新进来的连接的 TCP 的 Channel 。对于每一个新进来的连接，都会创建一个对应的 SocketChannel 。
DatagramChannel ：通过 UDP 读写数据。
FileChannel ：从文件中，读写数据。