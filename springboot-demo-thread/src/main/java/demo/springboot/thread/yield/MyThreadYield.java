package demo.springboot.thread.yield;

/**
 *
 *@author: bilahepan
 *@date: 2019/2/13 下午12:42
 */
class MyThreadYield extends Thread {

    MyThreadYield(String threadName) {
        super(threadName);
    }

    @Override
    public void run() {
        for (int i = 0; i <= 10; i++) {

            System.out.println(getName() + ":" + i);
            if (("t1").equals(getName())) {
                if (i < 5) {
                    yield();
                }
            }
        }
    }
}
