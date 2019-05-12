package demo.springboot.thread.join;

/**
 * @author: bilahepan
 * @date: 2018/10/18 上午9:50
 */
public class JoinTest {
    /**
     * Thread的非静态方法join()让一个线程B“加入”到另外一个线程A的尾部。在A执行完毕之前，B不能工作。
     */
    public static void main(String[] args) {
        parallelExecute();
        seqExecute();
        //
        System.out.println(Thread.currentThread().getName());
    }


    /**
     * 并行执行  t1,t2并行执行，最后main执行
     */
    public static void parallelExecute() {
        try {
            //
            MyJoinThread t1 = new MyJoinThread("nanme-1");
            MyJoinThread t2 = new MyJoinThread("nanme-2");
            t1.start();
            t2.start();


            //t1,t2并行执行
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * 子线程顺序执行，t2执行完毕后，t1执行，最后main线程执行
     */
    public static void seqExecute() {
        try {
            //
            MyJoinThread t1 = new MyJoinThread("nanme-1");
            MyJoinThread t2 = new MyJoinThread("nanme-2");
            t2.start();
            //等待t2执行完毕，t1未开启
            t2.join();

            //t1 开启
            t1.start();
            t1.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
