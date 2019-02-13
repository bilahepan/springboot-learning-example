package demo.springboot.thread.cyclicBarrier.demo2;

import java.util.concurrent.CyclicBarrier;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2018/10/19 下午6:46
 */
public class CycWork implements Runnable {


    private CyclicBarrier cyclicBarrier;
    private String name;

    public CycWork(CyclicBarrier cyclicBarrier, String name) {
        this.name = name;
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        try {
            System.out.println(name + "正在打桩，毕竟不轻松。。。。。");

            Thread.sleep(5000);
            System.out.println(name + "不容易，终于把桩打完了。。。。");
            cyclicBarrier.await();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(name + "：其他逗b把桩都打完了，又得忙活了。。。");
    }

}