package demo.springboot.thread.exercise.aliTest;

import java.util.concurrent.Callable;

/**
 * @author: bilahepan
 * @date: 2018/10/18 下午8:11
 */
public class PayTask implements Callable<ConsultResult> {
    //额外参数
    private String params;

    public PayTask(String params) {
        this.params = params;
    }

    @Override
    public ConsultResult call() {
        //TODO 此处执行具体的远程服务调用,返回改种支付方式是否可用
        //假定支付方式可用性咨询的远程服务接口定义 PaymentRemoteSerivce
        //模拟调用
        System.out.println(params);

        //假设返回成功
        return new ConsultResult(true, "");
        //返回失败
        //return new ConsultResult(false, "具体提示信息.");
    }
}