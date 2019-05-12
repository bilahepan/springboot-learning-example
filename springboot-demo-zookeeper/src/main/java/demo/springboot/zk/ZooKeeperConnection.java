package demo.springboot.zk;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.Watcher.Event.KeeperState;

/**
 * @author: bilahepan
 * @date: 2018/12/27 上午9:46
 */
public class ZooKeeperConnection {
    private ZooKeeper zoo;
    final CountDownLatch connectedSignal = new CountDownLatch(1);



    /**
     * 连接zk服务器
     * @param host 服务器地址
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public ZooKeeper connect(String host) throws IOException, InterruptedException {
        zoo = new ZooKeeper(host, 5000, new Watcher() {

            public void process(WatchedEvent we) {
                if (we.getState() == KeeperState.SyncConnected) {
                    connectedSignal.countDown();
                }
            }
        });

        connectedSignal.await();
        return zoo;
    }

    /**
     * 会话恢复
     *
     * @param host
     * @param sessionId
     * @param passwd
     * @return
     * @throws IOException
     */
    public ZooKeeper resumeConnect(String host, long sessionId, byte[] passwd) throws IOException {
        ZooKeeper zooKeeper = new ZooKeeper(host, 5000, new Watcher() {

            public void process(WatchedEvent we) {
                if (we.getState() == KeeperState.SyncConnected) {
                    connectedSignal.countDown();
                }
            }
        }, sessionId, passwd);

        return zooKeeper;

    }

    /**
     * 会话关闭
     *
     * @throws InterruptedException
     */
    public void close() throws InterruptedException {
        zoo.close();
    }


}