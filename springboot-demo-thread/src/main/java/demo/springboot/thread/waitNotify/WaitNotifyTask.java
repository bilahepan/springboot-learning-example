package demo.springboot.thread.waitNotify;

/**
 *
 *@author: 文若[gaotc@tuya.com]
 *@date: 2019/2/13 下午12:43
 */

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 任务处理
 */
class WaitNotifyTask extends Thread {

    private String threadName;
    private int num;
    private Object lock;
    private AtomicInteger count;

    public WaitNotifyTask(String threadName, AtomicInteger count, int num, Object lock) {
        super(threadName);
        this.count = count;
        this.num = num;
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            while (count.getAndAdd(1) <= 10) {
                synchronized (lock) {
                    System.out.println(Thread.currentThread().getName() + ":" + num);
                    lock.notifyAll();
                    lock.wait();//释放锁，释放cpu执行权
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}