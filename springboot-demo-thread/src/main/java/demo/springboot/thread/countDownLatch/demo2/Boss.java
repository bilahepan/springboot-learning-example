package demo.springboot.thread.countDownLatch.demo2;

import java.util.concurrent.CountDownLatch;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2018/10/19 下午6:23
 */
public class Boss implements Runnable {
    private CountDownLatch downLatch;

    public Boss(CountDownLatch downLatch) {
        this.downLatch = downLatch;
    }

    public void run() {
        try {
            System.out.println("老板正在等所有的工人干完活......");
            this.downLatch.await();//阻塞至0,开始后续执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("工人活都干完了，老板开始检查了！");
    }
}