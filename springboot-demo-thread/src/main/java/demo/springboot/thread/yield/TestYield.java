package demo.springboot.thread.yield;

/**
 * @author: 文若[gaotc@tuya.com]
 * @date: 2018/10/18 上午10:49
 */
public class TestYield {
    /**
     * yield()让当前正在运行的线程回到可运行状态，以允许具有相同优先级的其他线程获得运行的机会。
     * 因此，使用yield()的目的是让具有相同优先级的线程之间能够适当的轮换执行。
     * 但是，实际中无法保证yield()达到让步的目的，因为，让步的线程可能被线程调度程序再次选中。
     */
    public static void main(String[] args) {
        MyThreadYield t1 = new MyThreadYield("t1");
        MyThreadYield t2 = new MyThreadYield("t2");
        t1.start();
        t2.start();
    }
}