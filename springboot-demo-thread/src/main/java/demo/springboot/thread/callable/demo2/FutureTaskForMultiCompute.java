package demo.springboot.thread.callable.demo2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @author: bilahepan
 * @date: 2018/10/18 下午7:38
 */
public class FutureTaskForMultiCompute {
    public static void main(String[] args) {
        //
        //创建任务集合
        List<FutureTask<Integer>> futureList = new ArrayList<>();
        //创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(10);
        //
        for (int i = 0; i < 10; i++) {
            //创建Callable对象
            ComputeTask task = new ComputeTask(i, "taskName-" + i);
            //传入Callable对象，创建FutureTask对象
            FutureTask<Integer> futureTask = new FutureTask<>(task);
            //
            futureList.add(futureTask);
            //任务提交
            //也可以使用executor.invokeAll(taskList);一次性提交所有的任务
            executor.submit(futureTask);
        }
        //
        System.out.println("所有计算任务提交完毕,主线程接着做其他工作.");

        //开始统计各个计算线程计算结果
        Integer totalResult = 0;
        for (FutureTask<Integer> future : futureList) {
            try {
                //FutureTask 的get方法会自动阻塞，直到获取计算结果为止
                totalResult += future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //
        //关闭线程池
        executor.shutdown();
        //
        System.out.println("多任务计算后的总结果是:totalResult=" + totalResult);
    }
}