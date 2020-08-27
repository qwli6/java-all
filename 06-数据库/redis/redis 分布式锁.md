

> Redis 使用单个 Lua 脚本解释器去运行脚本，并且 Redis 会保证脚本以原子性（atomic）的方式执行；当某个脚本正在执行的时候，不会有其他的脚本或者命令被执行。



## 分布式锁

- 数据库的乐观锁
- 基于 Redis 的悲观锁
- 基于 zookeeper 的分布式锁。

锁的特点：

- 互斥性（排他性）
  - 任一时刻，必须只有一个客户端能获取锁
- 不能死锁
  - 客户端在持有锁的期间崩溃而没有主动解锁，也能保证后续其他客户端能加锁
- 容错性
  - 只要大部分的 Redis 集群还能正常工作，客户端就可以加锁和解锁



一般来说，在对数据进行「加锁」时，程序先要通过获取（acquire）锁来得到对数据进行排他性访问的能力，然后才能对数据执行一系列的操作，最后还要释放锁给其他的程序执行。

对于能够被多个线程访问的共享内存来说，这种「先获取锁，然后执行操作，最后释放锁」的动作非常常见。

Redis 中使用了 Watch 命令来构建了一个乐观锁，因为 Watch 命令只会在数据被其他客户端修改后通知执行了 Watch 这个命令的客户端，并不会阻止客户端对数据的修改。



因为 Redis 是单线程（6.0）之前的缘故，所以在 Redis 中分布式锁的操作既不是给同一个进程中的多个线程使用，也不是给同一台机器上的多个进程使用的，而是由不同的机器上的 Redis 客户端进行获取和释放的。所以针对 Redis 的这种情况，我们希望尽最大的可能将锁设置在 Redis 里面。



Redis 提供了一个 SETEX 命令来实现一个基本的加锁功能，但是这个功能并不完整。也不具备一些分布式锁的高级特性。



## 使用 Redis 构建锁（官方提供）

SETNX 命令天生就适合用来实现锁的功能，该命令只会在键不存在的情况下为键设置值，而锁要做的就是生成一个 128 位的 UUID 设置为键的值，并使用这个值来防止锁被其他的进程取得。

如果程序在获取锁的时候失败，那么它将不断地重试，知道成功地取得锁或者超过给定的时限为止。



**1.实现**

一般我们直接通过 set key value px millisecond nx 的命令实现加锁，通过 Lua 脚本实现解锁。

- 在早前的的 Redis 版本，通过 setnx + expire 命令来实现加锁，但是这两个操作无法实现原子性，因此在较高版本的 redis 中，redis 提供了 set key value px millisecond nx 的命令来实现原子性。

- value 需要保证唯一，这样在解锁的同时能有依据，而不会误解其他客户端的锁。
- 释放锁时，要验证 value 值，防止误解锁
- 释放锁需要三步，获取值，检查值，删除值，这三步通过 Lua 脚本来实现，保证原子性。



**2.存在的潜在风险**

如果存储锁对应 key 的那个节点挂了的话，就可能存在丢失锁的风险，导致出现多个客户端持有锁的情况，这样就不能实现资源的独享了。

- 客户端 A 从 master 获取到锁

- 在 master 将锁同步到 slave 之前，master 宕掉了（Redis 的主从同步通常是异步的），主从切换，slave 节点被晋级为 master 节点

- 客户端 B 取得了同一个资源被客户端 A 已经获取到的另外一个锁。导致存在同一时刻存不止一个线程获取到锁的情况。





## 使用 Redlock 算法构建锁（Redis 官方提供）

Redlock 算法是针对 Redis-cluster 的，假设现在有 5 个 Redis 服务

一般要执行如下几个步骤来获取锁

- 获取到当前系统的时间戳，单位是毫秒
- 轮流尝试在每台 Redis 节点上创建锁，过期时间较短，一般为几十毫秒
- 尝试在大多数节点上创建锁，这个节点数一般要为 n/2+1，也就是说只有在半数以上的 redis 服务器 + 1 个服务器上创建锁成功，才能叫获取到锁
- 客户端计算建立锁的时间，如果建立锁的时间小于超时时间，则分布式锁创建成功
- 只要创建锁失败，就会将之前建立好的锁删除
- 只要别人先建立了锁，你就得不断地轮询尝试去创建锁，知道创建成功为止。



---

但是以上的两种构建方式都是 Redis 官方提供的，也不能绝对安全。对于 Redis 提供的 Redlock 锁，在 16 年的时候就有争论，具体内容可以看

[官方介绍 redis 分布式锁和 Redlock（中文版）](http://www.redis.cn/topics/distlock.html)

[官方介绍 redis 分布式锁和 Redlock（英文版）](https://redis.io/topics/distlock)

[1. 关于 Redis 分布式锁是否安全的分析（上）](https://mp.weixin.qq.com/s?__biz=MzA4NTg1MjM0Mg==&mid=2657261514&idx=1&sn=47b1a63f065347943341910dddbb785d&chksm=84479e13b3301705ea29c86f457ad74010eba8a8a5c12a7f54bcf264a4a8c9d6adecbe32ad0b&scene=21#wechat_redirect)

[2. 关于 Redis 分布式锁是否安全的分析（下）](https://blog.csdn.net/jek123456/article/details/72954106)



## Redssion 构建分布式锁

Redssion 是一个在 Redis 基础上构建的 Java 常驻内存的分布式锁、优化了 Redis 的分布式锁的实现。

并且提供了多种语言实现的示例。

同时支持多种模式的锁，可重入锁，公平锁，读写锁，Redlock 等等。。



也支持多种模式的 Redis 结构，单机版 Redis，主从 Redis，哨兵 Redis，集群 Redis。



但是并未解决 Redis 当某一个节点宕机的时候，锁丢失的问题。





## 外卖里面使用 Redis 构建的分布式锁

```java
/**
 * 分布式锁帮助类
 * @author liqiwen
 * @since 2.2
 * @version 2.2
 * 2020-08-23 21:19:20
 */
public class DistributedLockHelper {

    /**
     * RedisTemplate
     */
    private final RedisTemplate<String, String> redisTemplate;

    public DistributedLockHelper(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    /**
     * 获取锁，默认锁超时时长是 10 s
     * //FIXME 10s 内获取锁失败，可能会导致多个客户端获取同一个锁
     * @param key key
     * @param value value
     * @return true | false
     */
    public boolean saveLock(String key, String value){
        Boolean lock = redisTemplate.opsForValue().setIfAbsent(key, value, Duration.ofSeconds(10));
        return lock != null && lock;
    }


    /**
     * 释放锁
     * 使用 lua 脚本释放锁，lua 脚本指定对 redis 来说是原子操作
     * @param key key
     * @param value value
     */
    public void saveUnlock(String key, String value){

        String luaScript = "local in = ARGV[1] local current = redis.call('get', KEYS[1]) if in==current then redis.call('del', KEYS[1]) end return 'OK'";
        RedisScript<String> redisScript = RedisScript.of(luaScript);

        redisTemplate.execute(redisScript, Collections.singletonList(key), Collections.singleton(value));
    }
}
```

