package demo.springboot.thread.join;

/**
 * @author: bilahepan
 * @date: 2019/2/13 下午12:44
 */
class MyJoinThread extends Thread {

    MyJoinThread(String threadName) {
        super(threadName);
    }

    @Override
    public void run() {
        System.out.println("My threadName is = " + super.getName());
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}