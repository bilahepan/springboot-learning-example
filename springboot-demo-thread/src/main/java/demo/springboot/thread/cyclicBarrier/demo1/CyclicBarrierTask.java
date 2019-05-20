package demo.springboot.thread.cyclicBarrier.demo1;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
/**
 * 栅栏-
 * 用于阻塞一组线程直到某个事件发生。
 * 所有线程必须同时到达栅栏位置，才能继续执行下一步操作。
 * 且可以被重置，以达到可以重复使用。
 *
 * @author: bilahepan
 * @date: 2018/10/15 下午2:09
 */
public class CyclicBarrierTask implements Runnable {

    private int threadNum;

    private CyclicBarrier barrier;

    public CyclicBarrierTask(int threadNum, CyclicBarrier barrier) {
        this.threadNum = threadNum;
        this.barrier = barrier;
    }


    @Override
    public void run() {
        //
        for (int index = 0; index < 2; index++) {
            try {
                System.out.println("线程" + threadNum + "第" + index + "次到达栅栏位置，等待其他线程到达");
                //进行阻塞等待
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
                return;
            }
        }
    }
}