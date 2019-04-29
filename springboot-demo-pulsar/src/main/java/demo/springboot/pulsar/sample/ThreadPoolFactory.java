package demo.springboot.pulsar.sample;

import java.util.concurrent.*;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/1/23 上午9:46
 */
public class ThreadPoolFactory {

    //获取线程池
    public static ExecutorService getCustomThreadPool() {
        return ThreadPoolFactory.CustomThreadPoolExecutorHolder.poolExecutor;
    }


    /**
     * 自定义线程池，拒绝策略-抛出异常RejectedExecutionException
     * 工作队列容量 5000
     * 核心线程数 3
     * 最大线程数 15
     *
     * @author: 文若[gaotc@tuya.com]
     * @date: 2018/12/4 下午4:13
     */
    public static class CustomThreadPoolExecutorHolder {
        /**
         * 线程池
         */
        private static final ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(3, 15, 30, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(5000),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
    }
}