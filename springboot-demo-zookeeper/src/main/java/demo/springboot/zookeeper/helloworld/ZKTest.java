package demo.springboot.zookeeper.helloworld;

import org.apache.zookeeper.ZooKeeper;

/**
 * @author: bilahepan
 * @date: 2019/5/12 下午5:24
 */
public class ZKTest {

    public static void main(String[] args) {
        try {
            String host = "127.0.0.1:2181";
            ZKConnectionDemo zk = new ZKConnectionDemo();
            ZooKeeper zkClient = zk.connect(host);
            long sessionId = zkClient.getSessionId();
            byte[] passWd = zkClient.getSessionPasswd();
            zkClient.close();
            ZooKeeper newZooKeeper = zk.resumeConnect(host, sessionId, passWd);
            System.err.printf(String.valueOf(newZooKeeper));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}