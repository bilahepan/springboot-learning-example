package demo.springboot.thread.threadpoolexecutor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newScheduledThreadPool;

public class ScheduledExecutorServiceDemo {

    public static void main(String[] args) {


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
