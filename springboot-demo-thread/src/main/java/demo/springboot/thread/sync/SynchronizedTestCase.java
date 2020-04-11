package demo.springboot.thread.sync;

/**
 * @author gaotianci
 */
public class SynchronizedTestCase implements Runnable {

    public synchronized void get() {
        System.out.println("1 run thread name-->" + Thread.currentThread().getName());
        System.out.println("2 enter thread name-->" + Thread.currentThread().getName());
        System.out.println("3 get thread name-->" + Thread.currentThread().getName());
        set();
        System.out.println("5 leave run thread name-->" + Thread.currentThread().getName());
    }

    public synchronized void set() {
        System.out.println("4 set thread name-->" + Thread.currentThread().getName());
    }

    @Override
    public void run() {
        get();
    }

    public static void main(String[] args) {
        SynchronizedTestCase test = new SynchronizedTestCase();
        for (int i = 0; i < 10; i++) {
            new Thread(test, "thread-" + i).start();
        }
    }

}
