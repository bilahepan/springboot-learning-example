package demo.springboot.thread.exercise.aliTest;

import java.util.concurrent.*;

/**
 * 线程池工厂
 *
 * @author: bilahepan
 * @date: 2018/10/18 下午8:27
 */
public class ThreadPoolFactory {
    //获取线程池
    public static ExecutorService getThreadPool() {
        return ThreadPoolHolder.POOL;
    }

    //静态内部类
    public static class ThreadPoolHolder {
        private static final ThreadPoolExecutor POOL =  new ThreadPoolExecutor(10, 10, 0L,TimeUnit.MINUTES,
                new LinkedBlockingDeque<Runnable>(10), Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
    }
}