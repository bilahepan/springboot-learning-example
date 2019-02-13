package demo.springboot.thread.exercise.aliTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池工厂
 *
 * @author: 文若[gaotc@tuya.com]
 * @date: 2018/10/18 下午8:27
 */
public class ThreadPoolFactory {

    //获取线程池
    public static ExecutorService getThreadPool() {
        return FixedPoolHolder.POOL;
    }

    //静态内部类
    public static class FixedPoolHolder {
        private static final ExecutorService POOL = Executors.newFixedThreadPool(10);
    }
}