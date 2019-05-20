
## 你的项目里有用到Netty?

## 哪些框架用到Netty，版本是？


## 异步和非阻塞一样吗？怎么理解
```text
https://blog.csdn.net/matthew_zhang/article/details/71328697
```

总结来说，在 Unix IO 模型的语境下：

同步和异步的区别：数据拷贝阶段是否需要完全由操作系统处理。
阻塞和非阻塞操作：是针对发起 IO 请求操作后，是否有立刻返回一个标志信息而不让请求线程等待。
因此，Java NIO 是同步且非阻塞的 IO 。