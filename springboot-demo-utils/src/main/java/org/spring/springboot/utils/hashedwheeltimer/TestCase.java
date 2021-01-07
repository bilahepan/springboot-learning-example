package org.spring.springboot.utils.hashedwheeltimer;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timer;
import io.netty.util.TimerTask;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestCase {
    public static void main(String[] args) {

        /**
         * 1.ThreadFactory 自定义线程工厂，用于创建线程对象。
         * 2.tickDuration  间隔多久走到下一槽（相当于时钟走一格）
         * 3.unit 定义tickDuration的时间单位
         * 4.ticksPerWheel 一圈有多个槽
         * 5.leakDetection 是否开启内存泄漏检测。
         * 6.maxPendingTimeouts 最多待执行的任务个数。0或负数表示无限制。
         */
        final Timer timer = new HashedWheelTimer(Executors.defaultThreadFactory(), 1, TimeUnit.SECONDS, 30);

        TimerTask task1 = new TimerTask() {
            @Override
            public void run(io.netty.util.Timeout timeout) throws Exception {
                System.out.println("task 1 will run per 5 seconds ");
                timer.newTimeout(this, 5, TimeUnit.SECONDS);//结束时候再次注册
            }
        };
        timer.newTimeout(task1, 5, TimeUnit.SECONDS);


        TimerTask task2 = new TimerTask() {
            @Override
            public void run(io.netty.util.Timeout timeout) throws Exception {
                System.out.println("task 2 will run per 10 seconds");
                timer.newTimeout(this, 10, TimeUnit.SECONDS);//结束时候再注册
            }
        };
        timer.newTimeout(task2, 10, TimeUnit.SECONDS);


        //该任务仅仅运行一次
        timer.newTimeout(new TimerTask() {
            @Override
            public void run(io.netty.util.Timeout timeout) throws Exception {
                System.out.println("task 3 run only once ! ");
            }
        }, 15, TimeUnit.SECONDS);

    }


    ConcurrentHashMap<String, HashMap<String,Integer>> mapConcurrentHashMap = new ConcurrentHashMap<>();
}