package demo.springboot.thread.callable.demo1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author: bilahepan
 * @date: 2018/10/18 下午7:21
 */
public class FutureExecutor {
    private static final int THREAD_NUMS = 10;

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_NUMS);

        List<Future<Long>> list = new ArrayList<>();
        for (long i = 0; i < THREAD_NUMS; i++) {
            Callable<Long> worker = new CallableTask(i * THREAD_NUMS + 1, (i + 1) * THREAD_NUMS);
            Future<Long> future = executor.submit(worker);
            list.add(future);
        }

        //
        long sum = 0;
        for (Future<Long> future : list) {
            try {
                sum += future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //
        executor.shutdown();
        System.out.println("sum=" + sum);
    }

}