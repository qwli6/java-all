





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

由于用的是阿里云的 redis 服务，暂时无法得知具体配置，需要问一下运维。





## 应用场景

商品的图片信息，描述，以及名称等等信息不易发生改变，可以将该内容缓存起来。从而获取更高的查询效率



如何保证订单号唯一

- 单机使用同步关键字来实现（加锁）
- 分布式使用 redis 自增属性来实现（redis 的操作都是原子性的）可以生成一个唯一的 id



redis 可以展示用户最近搜索过的数据



可以缓存所有的商品的名称，对商品进行模糊搜索，比如搜索「土」字的时候，实际上也是操作字符串数据。