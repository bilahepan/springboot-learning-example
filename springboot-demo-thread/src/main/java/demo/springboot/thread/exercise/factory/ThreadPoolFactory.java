package demo.springboot.thread.exercise.factory;

import java.util.concurrent.*;

/**
 * @author: bilahepan
 * @date: 2018/10/18 下午7:23
 */
public class ThreadPoolFactory {


    //获取缓存线程池
    public static ExecutorService getCachedPool() {
        return CachedPoolHolder.POOL;
    }

    /**
     * 获取定时调度的线程池
     *
     * @return
     */
    public static ScheduledExecutorService getScheduledPool() {
        return ScheduledThreadPoolHolder.ScheduledPool;
    }

    /**
     * 获取自定义的线程池
     *
     * @return
     */
    public static ThreadPoolExecutor getPoolExecutor() {
        return ThreadPoolExecutorHolder.poolExecutor;
    }


    //静态内部类
    public static class CachedPoolHolder {
        private static final ExecutorService POOL = Executors.newCachedThreadPool();
    }


    /**
     * 定时调度线程池
     *
     * @author bilahepan
     * @version $Id: ThreadPoolFactory.java, v 0.1 2018年1月5日 上午10:58:21 bilahepan Exp $
     */
    public static class ScheduledThreadPoolHolder {
        /**
         * 线程池
         */
        private static final ScheduledExecutorService ScheduledPool = new ScheduledThreadPoolExecutor(5);
    }

    /**
     * 创建自定义的线程池
     *
     * @author bilahepan
     * @version $Id: ThreadPoolFactory.java, v 0.1 2018年1月5日 下午2:12:27 bilahepan Exp $
     */
    public static class ThreadPoolExecutorHolder {
        /**
         * 线程池
         */
        private static final ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(40, 80, 30, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(10000),
                new ThreadPoolExecutor.DiscardPolicy());
    }

}