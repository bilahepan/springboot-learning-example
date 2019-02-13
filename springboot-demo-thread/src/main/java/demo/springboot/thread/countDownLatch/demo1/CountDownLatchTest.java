package demo.springboot.thread.countDownLatch.demo1;

import java.util.concurrent.CountDownLatch;

/**
 * 闭锁
 * 用于适合一组线程在同一时刻开始执行某个任务
 * 或者等待一组相关操作的结束
 *
 * @author: 文若[gaotc@tuya.com]
 * @date: 2018/10/15 下午1:01
 */
public class CountDownLatchTest {
    //统计耗时应用场景
    public void timeCostCount(int nThreads, Runnable task) throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);
        //
        for (int i = 0; i < nThreads; i++) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        startGate.await();
                        task.run();
                    } catch (InterruptedException e) {

                    } finally {
                        endGate.countDown();
                    }
                }
            };
            //
            thread.start();
        }
        long start = System.nanoTime();
        System.out.println("打开闭锁");
        //任务线程开始启动
        startGate.countDown();
        //阻塞至任务线程结束
        endGate.await();
        long end = System.nanoTime();
        System.out.println("闭锁退出，共耗时" + (end - start));
    }


    /**
     * main
     */
    public static void main(String[] args) throws InterruptedException {
        new CountDownLatchTest().timeCostCount(5, new CountDownLatchTask());
    }
}