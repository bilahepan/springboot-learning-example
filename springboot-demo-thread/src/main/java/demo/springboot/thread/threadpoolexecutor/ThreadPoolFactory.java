package demo.springboot.thread.threadpoolexecutor;

import java.util.concurrent.*;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/9/1 下午9:01
 */
public class ThreadPoolFactory {
    //        int queueSize = poolExecutor.getQueue().size();
    //        int activeCount = poolExecutor.getActiveCount();
    //        long completedTaskCount = poolExecutor.getCompletedTaskCount();
    //        long taskCount = poolExecutor.getTaskCount();
    //        //返回线程池中有史以来同时再池的最大数量
    //        //poolExecutor.getLargestPoolSize()
    //        long largestPoolSize = poolExecutor.getLargestPoolSize();
    /**  */
    public static ExecutorService getCustomThreadPool() {
        return ThreadPoolHolder.poolExecutor;
    }

    public static class ThreadPoolHolder {
        //线程池
        private static final ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(30, 30, 0L, TimeUnit.MINUTES,
                new LinkedBlockingDeque<Runnable>(5000), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
    }
}


