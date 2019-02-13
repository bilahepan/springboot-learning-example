package demo.springboot.thread.semaphore.demo3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2018/10/19 下午2:01
 */
public class SemaphoreDemo3 {

    private static final int SEM_MAX = 10;

    public static void main(String[] args) {
        Semaphore sem = new Semaphore(SEM_MAX,true);
        //创建线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        //在线程池中执行任务
        threadPool.execute(new Demo3Task(sem, 5));
        threadPool.execute(new Demo3Task(sem, 4));
        threadPool.execute(new Demo3Task(sem, 7));
        //关闭池
        threadPool.shutdown();
    }

}
