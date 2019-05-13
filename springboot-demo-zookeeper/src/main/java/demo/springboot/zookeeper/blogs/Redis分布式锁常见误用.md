

## Redis 实现分布式锁的常见误区和终极正确姿势

```
https://wudashan.cn/2017/10/23/Redis-Distributed-Lock-Implement/
```

## Redisson 实现分布式锁

不过我觉得有必要谈一谈Redisson和SETNX实现的锁的区别，Redisson的分布式锁是可重入(Reentrant)的，
然而SETNX实现的锁是不具有可重入性的。这在使用过程当中需要注意，不能简单的直接拿两者互换。

## Redisson 实现分布式锁的思路

```java


```