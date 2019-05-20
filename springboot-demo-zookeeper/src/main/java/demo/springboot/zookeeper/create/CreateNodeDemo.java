package demo.springboot.zookeeper.create;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author: bilahepan
 * @date: 2019/5/12 下午5:03
 */


/**
 * ## 创建节点(znode) 方法:
 * <p>
 * ```reStructuredText
 * create: 提供了两套创建节点的方法，同步和异步创建节点方式。
 * 同步方式创建，包含如下四个参数。
 * ```
 * <p>
 * ```shell
 * # 参数1
 * 节点路径名称:(不允许递归创建节点，也就是说在父节点不存在的情况下，不允许创建子节点)
 * <p>
 * # 参数2，节点内容:
 * 要求类型是字节数组（也就是说，不支持序列化方式，如果需要实现序列化，可使用java相关序列化框架，如Hessian、Kryo框架)
 * <p>
 * # 参数3，节点权限:
 * 使用Ids.OPEN_ACL_UNSAFE开放权限即可。(这个参数一般在权展没有太高要求的场景下，无需关注)
 * <p>
 * #参数4,节点类型:
 * 创建节点的类型: CreateMode，提供四种类型
 * PERSISTENT(持久节点)
 * PERSISTENT SEQUENTIAL(持久顺序节点)
 * EPHEMERAL(临时节点）
 * EPHEMERAL SEQUENTAL(临时顺序节点)
 * ```
 */

public class CreateNodeDemo {

    /**
     * 集群连接地址
     */
    private static final String CONNECT_ADDR = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
    /**
     * session超时时间
     */
    private static final int SESSION_OUTTIME = 2000;
    /**
     * 信号量,阻塞程序执行,用户等待zookeeper连接成功,发送成功信号
     */
    private static final CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZooKeeper zk = new ZooKeeper(CONNECT_ADDR, SESSION_OUTTIME, new Watcher() {

            public void process(WatchedEvent event) {
                // 获取时间的状态
                Event.KeeperState keeperState = event.getState();
                Event.EventType eventType = event.getType();
                // 如果是建立连接
                if (Event.KeeperState.SyncConnected == keeperState) {
                    if (Event.EventType.None == eventType) {
                        // 如果建立连接成功,则发送信号量,让后阻塞程序向下执行
                        countDownLatch.countDown();
                        System.out.println("zk 建立连接");
                    }
                }
            }

        });
        // 进行阻塞
        countDownLatch.await();
        //创建父节点
        String result = zk.create("/testRoot", "12245465".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("result:" + result);
        //创建子节点
        //String result = zk.create("/testRoot/children", "children 12245465".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        //System.out.println("result:" + result);
        zk.close();
    }
}