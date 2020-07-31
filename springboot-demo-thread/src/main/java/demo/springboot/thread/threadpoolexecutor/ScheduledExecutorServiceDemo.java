package demo.springboot.thread.threadpoolexecutor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newScheduledThreadPool;

public class ScheduledExecutorServiceDemo {

    public static void main(String[] args) {


        //1
        //Executors.newScheduledThreadPool()
        //public ScheduledThreadPoolExecutor(int corePoolSize) {
        //        super(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS,
        //              new DelayedWorkQueue());
        //    }

        //2
        //Executors.newSingleThreadExecutor()
        //public static ExecutorService newSingleThreadExecutor() {
        //        return new FinalizableDelegatedExecutorService
        //            (new ThreadPoolExecutor(1, 1,
        //                                    0L, TimeUnit.MILLISECONDS,
        //                                    new LinkedBlockingQueue<Runnable>()));
        //    }


        //3
        //Executors.newCachedThreadPool()
        //public static ExecutorService newCachedThreadPool() {
        //        return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
        //                                      60L, TimeUnit.SECONDS,
        //                                      new SynchronousQueue<Runnable>());
        //    }


        //4
        //Executors.newFixedThreadPool()
        //public static ExecutorService newFixedThreadPool(int nThreads) {
        //        return new ThreadPoolExecutor(nThreads, nThreads,
        //                                      0L, TimeUnit.MILLISECONDS,
        //                                      new LinkedBlockingQueue<Runnable>());
        //    }


        //默认拒绝策略
        //private static final RejectedExecutionHandler defaultHandler =
        //        new AbortPolicy();



        ScheduledExecutorService scheduledPool = newScheduledThreadPool(4);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println("开始:" + sdf.format(new Date()));
        //

        //Fixed 固定的
        //固定频率，不管任务是否结束
        scheduledPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("1,t=" + sdf.format(new Date()));
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {

                }
            }
        }, 3, 5, TimeUnit.SECONDS);


        //要等待任务执行结束，再计算延时间隔，循环执行
        scheduledPool.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                System.out.println("222,t=" + sdf.format(new Date()));
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {

                }
            }
        }, 3, 5, TimeUnit.SECONDS);

    }
}
