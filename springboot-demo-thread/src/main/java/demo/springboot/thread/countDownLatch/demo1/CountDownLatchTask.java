package demo.springboot.thread.countDownLatch.demo1;

/**
 * @author: bilahepan
 * @date: 2018/10/15 下午1:06
 */
public class CountDownLatchTask implements Runnable {
    @Override
    public void run() {
        System.out.println("当前线程为:" + Thread.currentThread().getName());
    }
}
