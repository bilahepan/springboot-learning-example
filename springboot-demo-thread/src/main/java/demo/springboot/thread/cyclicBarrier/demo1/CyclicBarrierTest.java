package demo.springboot.thread.cyclicBarrier.demo1;

import java.util.concurrent.CyclicBarrier;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2018/10/15 下午2:08
 */
public class CyclicBarrierTest {

    private final CyclicBarrier barrier;
    private final CyclicBarrierTask[] workers;

    /**
     * 构造器中进行参数的初始化设置
     */
    public CyclicBarrierTest() {
        int count = 2;
        //barrier初始化
        this.barrier = new CyclicBarrier(count,
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("所有线程均到达栅栏位置，开始下一轮计算");
                    }

                });
        //workers初始化
        this.workers = new CyclicBarrierTask[count];
        for (int i = 0; i < count; i++) {
            workers[i] = new CyclicBarrierTask(i, barrier);
        }
    }


    /**
     * 启动方法
     */
    public void start() {
        for (int i = 0; i < workers.length; i++) {
            new Thread(workers[i]).start();
        }
    }

    /**
     * main
     */
    public static void main(String[] args) {
        new CyclicBarrierTest().start();
    }
}