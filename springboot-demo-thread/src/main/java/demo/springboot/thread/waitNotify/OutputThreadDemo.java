package demo.springboot.thread.waitNotify;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2018/10/18 上午11:12
 */
public class OutputThreadDemo {

    static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) {
        final Object lock = new Object();

        Thread thread1 = new WaitNotifyTask("thread-1", count, 1, count);
        Thread thread2 = new WaitNotifyTask("thread-2", count, 2, count);

        thread1.start();
        thread2.start();
    }
}
