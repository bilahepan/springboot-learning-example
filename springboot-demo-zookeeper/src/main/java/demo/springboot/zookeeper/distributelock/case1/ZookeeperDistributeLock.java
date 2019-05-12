package demo.springboot.zookeeper.distributelock.case1;

import org.I0Itec.zkclient.IZkDataListener;

import java.util.concurrent.CountDownLatch;

/**
 * @author: bilahepan
 * @date: 2019/5/12 下午9:21
 */

/**
 * 分布式锁解决思路
 * 分布式锁使用zk，在zk上创建一个临时节点（有效期)，使用临时节点作为锁，因为节点不允许重复。
 * 如果能创建节点成功，生成订单号，如果创建节点失败，等待。临时节点zk关闭，释放锁，其他节点就可以重新生成订单号。
 */

//TODO 超时时间参数未设置

public class ZookeeperDistributeLock extends ZookeeperAbstractLock {
    private CountDownLatch countDownLatch = null;

    @Override
    boolean tryLock() {
        try {
            zkClient.createEphemeral(PATH);
            return true;
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }

    }

    @Override
    void waitLock() {
        IZkDataListener izkDataListener = new IZkDataListener() {
            public void handleDataDeleted(String path) throws Exception {
                // 唤醒被等待的线程
                if (countDownLatch != null) {
                    countDownLatch.countDown();
                }
            }

            public void handleDataChange(String path, Object data) throws Exception {

            }
        };

        // 注册事件
        zkClient.subscribeDataChanges(PATH, izkDataListener);

        if (zkClient.exists(PATH)) {
            countDownLatch = new CountDownLatch(1);
            try {
                countDownLatch.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 删除监听
        zkClient.unsubscribeDataChanges(PATH, izkDataListener);
    }

}