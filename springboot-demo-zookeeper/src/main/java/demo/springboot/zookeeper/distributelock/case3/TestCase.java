package demo.springboot.zookeeper.distributelock.case3;

/**
 * @author: bilahepan
 * @date: 2019/5/12 下午10:46
 */
public class TestCase {
    public static void main(String[] args) {
        LockService lockService = new LockService();
        for (int i = 0; i < 100; i++) {
            new Thread(lockService).start();
        }
    }
}