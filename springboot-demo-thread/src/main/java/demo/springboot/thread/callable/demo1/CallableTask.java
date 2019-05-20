package demo.springboot.thread.callable.demo1;

import java.util.concurrent.Callable;

/**
 * @author: bilahepan
 * @date: 2018/10/18 下午7:19
 */
public class CallableTask implements Callable<Long> {

    private Long start;

    private Long end;

    public CallableTask(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public Long call() throws Exception {
        long sum = 0;
        for (long i = start; i <= end; i++) {
            sum += i;
        }
        System.out.println(Thread.currentThread().getName() + " " + start + "-" + end + "-" + sum);
        return sum;
    }
}