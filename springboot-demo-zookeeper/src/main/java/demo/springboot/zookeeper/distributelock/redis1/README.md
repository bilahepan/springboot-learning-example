
## 基于Redis的分布式锁

---------------
在使用Redis实现分布式锁的时候，主要就会使用到这三个命令。
（1）SETNX
    SETNX key val当且仅当key不存在时，set一个key为val的字符串，返回1；若key存在，则什么都不做，返回0。
（2）Expire
    expire key timeout。为key设置一个超时时间，单位为second，超过这个时间锁会自动释放，避免死锁。
（3）Delete
    delete key，删除key。
    
---------------

实现原理
使用的是jedis来连接Redis。

实现思路
1.获取锁的时候，使用setnx加锁，并使用expire命令为锁添加一个超时时间，超过该时间则自动释放锁，锁的value值为一个随机生成的UUID，通过此在释放锁的时候进行判断。
2.获取锁的时候还设置一个获取的超时时间，若超过这个时间则放弃获取锁。
3.释放锁的时候，通过UUID判断是不是该锁，若是该锁，则执行delete进行锁释放。
    
 