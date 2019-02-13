package demo.springboot.thread.exercise.aliTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2018/10/18 下午7:57
 */
public class MainTest {
    public static void main(String[] args) throws TimeoutException {

        //minPayNum 代表根据需求设置需要获取的最少可用支付方式
        int minPayNum =1;
        List<String> payList = new ArrayList<>();
        payList.add("余额");
        payList.add("红包");
        payList.add("抵用券");
        payList.add("优惠券");
        payList.add("代金券");
        payList.add("其它");

        //任务集合
        List<Future<ConsultResult>> futureList = new ArrayList<Future<ConsultResult>>();

        //任务提交
        for (String item : payList) {
            futureList.add(ThreadPoolFactory.getThreadPool().submit(new PayTask(item)));
        }

        //根据具体情况设置超时等待时间，这里设置了最大20000ms
        List<Long> timeList = new ArrayList<>();
        timeList.add(1000L);
        timeList.add(2000L);
        timeList.add(3000L);
        timeList.add(4000L);
        timeList.add(10000L);
        timeList.add(20000L);

        //结果集合
        List<ConsultResult> result = new ArrayList<>();

        //获取结果
        for (Long time : timeList) {
            for (Future<ConsultResult> future : futureList) {
                try {
                    ConsultResult consultResult = future.get(time, TimeUnit.MILLISECONDS);
                    if (consultResult.getIsEnable()) {
                        result.add(consultResult);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            //记录下，当前设置时间内返回了几个可用支付方式
            //Logger.info(result.size,times)
            //达到了预设的最少期望的可用支付方式，就直接返回
            if (result.size() >= minPayNum) {
                //Logger.info(result.size,times)
                break;
            }
            //达到最大超时时间，且获取到的可用支付方式为0,记录error日志,根据业务逻辑抛自定义异常,报警等
            if (time == 20000L && result.size() == 0) {
                // Logger.error()
            }
        }
        //输出结果
        System.out.println(result);
    }
}