

1 Channel 
http://svip.iocoder.cn/Netty/nio-2-channel/
2 Buffer
http://svip.iocoder.cn/Netty/nio-3-buffer/
3 Selector
http://svip.iocoder.cn/Netty/nio-4-selector/

1. 概述
一个 Buffer ，本质上是内存中的一块，我们可以将数据写入这块内存，之后从这块内存获取数据。
通过将这块内存封装成 NIO Buffer 对象，并提供了一组常用的方法，方便我们对该块内存的读写。

我们可以将 Buffer 理解为一个数组的封装，
例如 IntBuffer、CharBuffer、ByteBuffer 等分别对应 int[]、char[]、byte[] 等。

