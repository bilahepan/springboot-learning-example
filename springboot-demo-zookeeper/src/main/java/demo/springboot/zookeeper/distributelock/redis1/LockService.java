package demo.springboot.zookeeper.distributelock.redis1;

import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author: bilahepan
 * @date: 2019/5/12 下午10:43
 */
public class LockService implements Runnable{
    private static JedisPool jedisPool = null;

    private static final String REDIS_ADDR = "127.0.0.1";

    private static final Integer REDIS_ADDR_PORT = 6379;

    static {
        JedisPoolConfig config = new JedisPoolConfig();
        // 设置最大连接数
        config.setMaxTotal(200);
        // 设置最大空闲数
        config.setMaxIdle(8);
        // 设置最大等待时间
        config.setMaxWaitMillis(1000 * 100);
        // 在borrow一个jedis实例时，是否需要验证，若为true，则所有jedis实例均是可用的
        config.setTestOnBorrow(true);
        //
        jedisPool = new JedisPool(config, REDIS_ADDR, REDIS_ADDR_PORT, 3000);
    }

    RedisLock lockRedis = new RedisLock(jedisPool);

    public void processTask() {
        String lockFlagValue = lockRedis.getLock("tc_flight_tgq", 5000l, 5000l);
        if (StringUtils.isEmpty(lockFlagValue)) {
            // 获取锁失败
            System.out.println(Thread.currentThread().getName() + ",获取锁失败，原因时间超时!!!");
            return;
        }

        //业务处理
        System.out.println(Thread.currentThread().getName() + "获取锁成功,锁id lockFlagValue:" + lockFlagValue + "，执行业务逻辑.");
        try {
            Thread.sleep(30);
        } catch (Exception e) {
        }

        // 释放锁
        boolean releaseLock = lockRedis.releaseLock("tc_flight_tgq", lockFlagValue);
        if (releaseLock) {
            System.out.println(Thread.currentThread().getName() + "释放锁成功,锁id lockFlagValue:" + lockFlagValue);
        }
    }

    @Override
    public void run() {

        processTask();

    }
}