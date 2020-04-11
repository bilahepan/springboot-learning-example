package demo.springboot.thread.reentrantLock;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTestCase implements Runnable {


    //可重入锁，默认的确是非公平锁
    private ReentrantLock reentrantLock = new ReentrantLock();

    public void get() {
        reentrantLock.lock();
        try {
            Long start = System.currentTimeMillis();
            System.out.println("1 run thread name-->" + Thread.currentThread().getName());
            System.out.println("2 enter thread name-->" + Thread.currentThread().getName());
            System.out.println("3 get thread name-->" + Thread.currentThread().getName());
            set();
            System.out.println("5 leave run thread name-->" + Thread.currentThread().getName() + " ;cost=" + (System.currentTimeMillis() - start));
        } finally {
            reentrantLock.unlock();
        }
    }

    public void set() {
        reentrantLock.lock();
        try {
            System.out.println("4 set thread name-->" + Thread.currentThread().getName());
        } finally {
            reentrantLock.unlock();

        }
    }

    @Override
    public void run() {
        get();
    }

    public static void main(String[] args) {
        ReentrantLockTestCase test = new ReentrantLockTestCase();
        for (int i = 0; i < 10; i++) {
            new Thread(test, "thread-" + i).start();
        }
    }


}


