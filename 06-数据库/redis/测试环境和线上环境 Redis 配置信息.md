





## 公司测试环境 Redis 配置

> 测试环境的配置就是用的默认配置，测试环境 redis 版本为 3.0.2
>
> aof 未开启，默认为快照备份

当无法找到 redis 的安装目录时（使用 which 和 where 都无法找到），可以使用以下命令找到 redis 的安装目录

```shell
[root@nginx-19-60 21:25 /etc]
# ps -ef | grep redis
root      5971     1  9 Aug01 ?        2-10:17:08 redis-server *:6379
root     32813  5971 63 21:25 ?        00:00:31 redis-rdb-bgsave *:6379
root     32828 32605  0 21:26 pts/2    00:00:00 grep redis

# 找到 redis 进程号

[root@nginx-19-60 21:26 /etc]
# ls -l /proc/5971/cwd

lrwxrwxrwx 1 root root 0 Aug 26 21:27 /proc/5971/cwd -> /usr/local/src/redis/redis-3.0.2/src
```

先找到 redis 的进程号，使用 ls -l /proc/进程号/cwd 可以找到 redis 的安装目录

![image-20200826213248359](/Users/liqiwen/Library/Application Support/typora-user-images/image-20200826213248359.png)

可以看到 dump.rdb 的快照文件，为什么这个文件这么久都没有更新？因为不满足 save 条件。😆😆

> 注意 dir 目录配置的是相对路径，**如果是默认的，那么快照文件和 aof 文件应该默认生成在 redis 的安装目录下**。



## 公司生产环境 redis 配置

> 生产版本使用的是 Redis2.8.19，但是兼容 Redis 3.0 的特性

![image-20200827091715578](/Users/liqiwen/Library/Application Support/typora-user-images/image-20200827091715578.png)

16G 内存，4 个节点

![image-20200827091905386](/Users/liqiwen/Library/Application Support/typora-user-images/image-20200827091905386.png)

![image-20200827094333108](/Users/liqiwen/Library/Application Support/typora-user-images/image-20200827094333108.png)



4 个节点上每个节点都部署了两个 redis，一主一从，从节点只是为了备份主节点数据，不对外提供服务。

现在外卖线上使用的就是 Redis-Cluster

线上默认的备份方式是 rdb + aof（快照模式，默认保留 10 天的备份）

![image-20200827103159003](/Users/liqiwen/Library/Application Support/typora-user-images/image-20200827103159003.png)



使用 info 查看线上的 Redis 信息

```properties
# Cluster
databases:256 #256个数据库
nodecount:4  #4个节点
```

更多信息可以使用 redis 的 info 信息查看。



## Redis 相关应用场景

商品的图片信息，描述，以及名称等等信息不易发生改变，可以将该内容缓存起来。从而获取更高的查询效率



如何保证订单号唯一

- 单机使用同步关键字来实现（加锁）
- 分布式使用 redis 自增属性来实现（redis 的操作都是原子性的）可以生成一个唯一的 id



redis 可以展示用户最近搜索过的数据



可以缓存所有的商品的名称，对商品进行模糊搜索，比如搜索「土」字的时候，实际上也是操作字符串数据。



可以存储微信 QQ A 和 B 好友的聊天记录，A 和 B 虽然只有两个人，但是也可以看做是一个群组，只是只有两个人而已，

用群 id 作为 key，zset 有序集合存放聊天记录内容。分值可以按照时间戳来进行存储。

（微信用户）如果用户删除会话，可以将消息持久化到 DB 或者硬盘上，防止用户恢复聊天记录。