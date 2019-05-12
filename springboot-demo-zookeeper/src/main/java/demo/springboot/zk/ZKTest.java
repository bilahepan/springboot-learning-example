package demo.springboot.zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import sun.tools.asm.CatchData;

import java.nio.charset.Charset;

/**
 * @author: bilahepan
 * @date: 2018/12/28 下午1:01
 */
public class ZKTest {

    public static void main(String[] args) {
        try {
            String host = "127.0.0.1:2181";
            ZooKeeperConnection zooKeeperConnection = new ZooKeeperConnection();
            ZooKeeper zooKeeper = zooKeeperConnection.connect(host);
            long sessionId = zooKeeper.getSessionId();
            byte[] passWd = zooKeeper.getSessionPasswd();
            zooKeeper.close();
            ZooKeeper newZooKeeper = zooKeeperConnection.resumeConnect(host, sessionId, passWd);
            System.err.printf(String.valueOf(newZooKeeper));
        } catch (Exception e) {
            System.out.println(e);
        }

        //-------------------------------------// //-------------------------------------//
        //http://blog.51cto.com/zero01/2108483 权限操作


    }
}
