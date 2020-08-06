
##grpc 说明参考

```text
https://github.com/guangxush/SpringBoot_GRPC
```

第一种方式可以用阻塞式的请求，但其他三种涉及到流的都必须使用非阻塞，这个调用关系gRPC已经在代码中强制给我们规范好了，在阻塞式的BlockingStub中将无法请求到流式的方法。
特别是第三种方式也即是客户端流式RPC方式，请求一个流，响应一个非流的情况，我们必须在请求的流传入结束之后才能进行结果响应，
否则会获得一个警告: Cancelling the stream with status Status{code=INTERNAL, description=Too many responses, cause=null}，
很明显提示Too many response，因为不是流式响应所以只能有一个Response，最终得到错误的结果。