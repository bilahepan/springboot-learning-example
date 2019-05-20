package demo.springboot.thread.semaphore.demo3;

import java.util.concurrent.Semaphore;

/**
 * @author: bilahepan
 * @date: 2018/10/19 下午2:03
 */
public class Demo3Task extends Thread {
    private volatile Semaphore sem;// 信号量
    private int count;// 申请信号量的大小

    Demo3Task(Semaphore sem, int count) {
        this.sem = sem;
        this.count = count;
    }

    public void run() {
        try {
            //从信号量中获取count个许可
            sem.acquire(count);
            //模拟任务处理耗时
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " acquire count=" + count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 释放给定数目的许可，将其返回到信号量。
            sem.release(count);
            System.out.println(Thread.currentThread().getName() + " release " + count + "");
        }
    }
}