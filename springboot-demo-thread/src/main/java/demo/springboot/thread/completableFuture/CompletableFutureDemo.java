package demo.springboot.thread.completableFuture;


import java.util.concurrent.CompletableFuture;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2018/10/18 上午9:39
 */
public class CompletableFutureDemo {

    /**
     * 关键的入参只有一个Function，它是函数式接口，所以使用Lambda表示起来会更加优雅。
     * 它的入参是上一个阶段计算后的结果，返回值是经过转化后结果。
     */
    public void thenApply() {
        String result = CompletableFuture.supplyAsync(() -> "hello").thenApply(s -> s + " world").join();
    }
}