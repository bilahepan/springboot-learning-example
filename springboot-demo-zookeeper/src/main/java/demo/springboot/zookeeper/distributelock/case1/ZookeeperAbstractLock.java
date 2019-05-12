package demo.springboot.zookeeper.distributelock.case1;

import org.I0Itec.zkclient.ZkClient;

/**
 * @author: bilahepan
 * @date: 2019/5/12 下午9:10
 */
public abstract class ZookeeperAbstractLock implements Lock {
    //集群连接地址
    private static final String CONNECT_ADDR = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
    // 创建zk连接
    protected ZkClient zkClient = new ZkClient(CONNECT_ADDR);
    protected static final String PATH = "/lock";

    public void getLock() {
        if (tryLock()) {
            System.out.println("##获取lock锁的资源####");
        } else {
            // 等待
            waitLock();
            // 重新获取锁资源
            getLock();
        }

    }

    // 获取锁资源
    abstract boolean tryLock();

    // 等待
    abstract void waitLock();

    public void releaseLock() {
        if (zkClient != null) {
            zkClient.close();
            System.out.println("释放锁资源...");
        }
    }

}