package demo.springboot.thread.forkjoin;

import java.util.concurrent.ForkJoinPool;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2019/8/27 下午5:18
 */
public class DemoTest {

    public static void main(String[] args) {
        //1kw
        ForkJoinImpl forkJoin = new ForkJoinImpl(0, 10000000);
        Long invoke = new ForkJoinPool().invoke(forkJoin);
        System.out.println("invoke = " + invoke);
    }
}