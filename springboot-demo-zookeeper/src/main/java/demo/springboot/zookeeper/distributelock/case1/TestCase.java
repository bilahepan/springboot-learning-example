package demo.springboot.zookeeper.distributelock.case1;
/**
 * @author: bilahepan
 * @date: 2019/5/12 下午9:46
 */
public class TestCase implements Runnable {
    private OrderNumGenerator orderNumGenerator = new OrderNumGenerator();
    // 使用lock锁
    // private java.util.concurrent.locks.Lock lock = new ReentrantLock();
    private Lock lock = new ZookeeperDistributeLock();

    public void run() {
        getNumber();
    }

    public void getNumber() {
        try {
            lock.getLock();
            String number = orderNumGenerator.getNumber();
            System.err.println(Thread.currentThread().getName() + ",生成订单ID:" + number);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.releaseLock();
        }
    }

    public static void main(String[] args) {
        System.err.println("####生成唯一订单号###");
        //OrderService orderService = new OrderService();
        for (int i = 0; i < 100; i++) {
            new Thread(new TestCase()).start();
        }
    }
}