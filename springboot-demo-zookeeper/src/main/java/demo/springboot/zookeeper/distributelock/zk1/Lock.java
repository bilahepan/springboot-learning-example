package demo.springboot.zookeeper.distributelock.zk1;

/**
 * @author: bilahepan
 * @date: 2019/5/12 下午9:09
 */
public interface Lock {
    //获取到锁的资源
    public void getLock();

    // 释放锁
    public void releaseLock();
}
