package demo.springboot.zookeeper.watcher;

/**
 * @author: bilahepan
 * @date: 2019/5/12 下午6:17
 */

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * 回调方法 process()
 * <p>
 * process 方法是 Watcher 接口中的一个回调方法，当 ZooKeeper 向客户端发送一个 Watcher 事件通知时，
 * 客户端就会对相应的 process 方法进行回调，从而实现对事件的处理。
 * <p>
 * process方法的定义如下：
 * <p>
 * abstract public void process(WatchedEvent event);
 * <p>
 * <p>
 * 这个回调方法的定义非常简单，我们重点看下方法的参数定义：WatchedEvent。
 * WatchedEvent 包含了每一个事件的三个基本属性：通知状态（keeperState），事件类型（EventType）和节点路径（path）。
 * ZooKeeper 使用 WatchedEvent 对象来封装服务端事件并传递给 Watcher，从而方便回调方法 process 对服务端事件进行处理。
 * 提到 WatchedEvent，不得不讲下 WatcherEvent 实体。笼统地讲，两者表示的是同一个事物，都是对一个服务端事件的封装。不同的是，WatchedEvent 是一个逻辑事件，用于服务端和客户端程序执行过程中所需的逻辑对象，而 WatcherEvent 因为实现了序列化接口，因此可以用于网络传输。服务端在生成 WatchedEvent 事件之后，会调用 getWrapper 方法将自己包装成一个可序列化的 WatcherEvent 事件，以便通过网络传输到客户端。
 * 客户端在接收到服务端的这个事件对象后，首先会将 WatcherEvent 还原成一个 WatchedEvent 事件，并传递给 process 方法处理，回调方法 process 根据入参就能够解析出完整的服务端事件了。
 * 需要注意的一点是，无论是 WatchedEvent 还是 WatcherEvent，其对 ZooKeeper 服务端事件的封装都是机及其简单的。
 */

public class ZkClientWatcher implements Watcher {
    // 集群连接地址
    private static final String CONNECT_ADDRES = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
    // 会话超时时间
    private static final int SESSIONTIME = 2000;
    // 信号量,让zk在连接之前等待,连接成功后才能往下走.
    private static final CountDownLatch countDownLatch = new CountDownLatch(1);
    private static String LOG_MAIN = "【main】 ";
    private ZooKeeper zk;

    public void createConnection(String connectAddres, int sessionTimeOut) {
        try {
            zk = new ZooKeeper(connectAddres, sessionTimeOut, this);
            System.out.println(LOG_MAIN + "zk 开始启动连接服务器....");
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean createPath(String path, String data) {
        try {
            this.exists(path, true);
            this.zk.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            System.out.println(LOG_MAIN + "节点创建成功, Path:" + path + ",data:" + data);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断指定节点是否存在
     *
     * @param path 节点路径
     */
    public Stat exists(String path, boolean needWatch) {
        try {
            return this.zk.exists(path, needWatch);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateNode(String path, String data) throws KeeperException, InterruptedException {
        exists(path, true);
        this.zk.setData(path, data.getBytes(), -1);
        return false;
    }

    public void process(WatchedEvent watchedEvent) {
        // 获取事件状态
        Event.KeeperState keeperState = watchedEvent.getState();
        // 获取事件类型
        Event.EventType eventType = watchedEvent.getType();
        // zk 路径
        String path = watchedEvent.getPath();
        System.out.println("进入到 process() keeperState:" + keeperState + ", eventType:" + eventType + ", path:" + path);
        // 判断是否建立连接
        if (Event.KeeperState.SyncConnected == keeperState) {
            if (Event.EventType.None == eventType) {
                // 如果建立建立成功,让后程序往下走
                System.out.println(LOG_MAIN + "zk 建立连接成功!");
                countDownLatch.countDown();
            } else if (Event.EventType.NodeCreated == eventType) {
                System.out.println(LOG_MAIN + "事件通知,新增node节点" + path);
            } else if (Event.EventType.NodeDataChanged == eventType) {
                System.out.println(LOG_MAIN + "事件通知,当前node节点" + path + "被修改....");
            } else if (Event.EventType.NodeDeleted == eventType) {
                System.out.println(LOG_MAIN + "事件通知,当前node节点" + path + "被删除....");
            }

        }
        System.out.println("--------------------------------------------------------");
    }

    public static void main(String[] args) throws KeeperException, InterruptedException {
        ZkClientWatcher zkClientWatcher = new ZkClientWatcher();
        zkClientWatcher.createConnection(CONNECT_ADDRES, SESSIONTIME);
		boolean createResult = zkClientWatcher.createPath("/p15", "pa-644064");
        //zkClientWatcher.updateNode("/pa2", "7894561");
    }
}