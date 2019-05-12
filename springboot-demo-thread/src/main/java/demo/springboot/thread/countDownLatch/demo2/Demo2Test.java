package demo.springboot.thread.countDownLatch.demo2;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: bilahepan
 * @date: 2018/10/19 下午6:25
 */
public class Demo2Test {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();

        CountDownLatch latch = new CountDownLatch(3);

        Worker w1 = new Worker(latch, "张三");
        Worker w2 = new Worker(latch, "李四");
        Worker w3 = new Worker(latch, "王二");

        Boss boss = new Boss(latch);

        executor.submit(w1);
        executor.submit(w2);
        executor.submit(w3);
        executor.execute(boss);
//        executor.execute(w3);
//        executor.execute(w2);
//        executor.execute(w1);
//        executor.execute(boss);

        executor.shutdown();
    }
}