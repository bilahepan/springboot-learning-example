package demo.springboot.zookeeper.distributelock.redis1;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.UUID;

/**
 * @author: bilahepan
 * @date: 2019/5/12 下午10:40
 */
public class RedisLock {

    private JedisPool jedisPool;

    public RedisLock(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * @param lockKey        锁的key<br>
     * @param acquireTimeout 在没有上锁之前,获取锁的超时时间<br>
     * @param timeOut        上锁成功后,锁的超时时间<br>
     * @return
     */
    public String getLock(String lockKey, Long acquireTimeout, Long timeOut) {
        Jedis conn = null;
        String lockFlagValue = null;
        try {
            // 1.建立redis连接
            conn = jedisPool.getResource();
            // 2.随机生成一个value
            String identifierValue = UUID.randomUUID().toString();
            // 3.定义锁的名称
            String lockName = "redis_lock" + lockKey;
            // 4.定义上锁成功之后,锁的超时时间
            int expireLock = (int) (timeOut / 1000);
            // 5.定义在没有获取锁之前,锁的超时时间
            Long endTime = System.currentTimeMillis() + acquireTimeout;
            while (System.currentTimeMillis() < endTime) {
                // 6.使用setnx方法设置锁值
                if (conn.setnx(lockName, identifierValue) == 1) {
                    // 7.判断返回结果如果为1,则可以成功获取锁,并且设置锁的超时时间
                    conn.expire(lockName, expireLock);
                    lockFlagValue = identifierValue;
                    return lockFlagValue;
                }
                // 8.否则情况下继续循环等待
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return lockFlagValue;
    }

    /**
     * 释放锁
     *
     * @return
     */
    public boolean releaseLock(String lockKey, String lockFlagValue) {

        Jedis conn = null;
        boolean flag = false;
        try {
            // 1.建立redis连接
            conn = jedisPool.getResource();
            // 2.定义锁的名称
            String lockName = "redis_lock" + lockKey;
            // 3.如果value与redis中一致直接删除，否则等待超时
            if (lockFlagValue.equals(conn.get(lockName))) {
                conn.del(lockName);
                System.out.println(lockFlagValue + "解锁成功......");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return flag;
    }
}