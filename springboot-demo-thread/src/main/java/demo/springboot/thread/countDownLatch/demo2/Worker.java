package demo.springboot.thread.countDownLatch.demo2;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2018/10/19 下午6:15
 */
public class Worker implements Runnable {
    private CountDownLatch downLatch;
    private String name;

    public Worker(CountDownLatch downLatch, String name) {
        this.downLatch = downLatch;
        this.name = name;
    }

    public void run() {
        try {
            this.doWork();
            TimeUnit.SECONDS.sleep(new Random().nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(this.name + "活干完了！");
        this.downLatch.countDown();
    }

    private void doWork() {
        System.out.println(this.name + "正在干活!");
    }


}