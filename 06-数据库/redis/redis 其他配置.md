## Redis 其他配置

在 redis.conf 中，配置了 databases 的数量，默认配置的是 16，也就是我们在 rdm 里面看到的 db0~db15



## 1.Redis 数据库 db0~db15

- 默认情况下 redis 会生成 db0~db15 共 16 个 db
- 不同的数据库，key 相互独立

## 2.修改默认生成的数据库个数配置

在默认的 redis.conf 中，可以修改数据库个数的配置，目前只能在配置文件中更改

> 基于 redis6.0 的 redis.conf 中
>
> 默认的数据库是 db0，在 redis-cli 中，可以通过 select <dbid> 来选择数据库，dbid 一般选择为 0~num-1

```properties
# redis 的配置文件中
databases 16
```

## 3.切换数据库

在 redis-cli 中，切换数据库

```shell
# 切换到第二个数据库
127.0.0.1:6379> select 1
OK
```

获取当前的数据库个数

```shell
127.0.0.1:6379> config get databases
1) "databases"
2) "16"
```

如果切换到了不存在 db， redis 会报错。

> 默认使用的 db 是 db0，至于为什么是 db0，根据 SpringDataRedis 的类 JedisConnectionFactory 中的 org.springframework.data.redis.connection.jedis.JedisConnectionFactory#getDatabase 显示 database 它是一个 int 类型，并不是包装类型，如果没有设置值，那么默认操作就是 db0，

```java
public int getDatabase() {
   RedisConfiguration var10000 = this.configuration;
   RedisStandaloneConfiguration var10001 = this.standaloneConfig;
   var10001.getClass();
  //从 var10000 里面取或者从 var10001 里面的 getDatabase 方法里面取
   return RedisConfiguration.getDatabaseOrElse(var10000, var10001::getDatabase); 
}

//先看 RedisStandaloneConfiguration 里面的 getDatabase 方法
public int getDatabase() {
    return this.database; //这里不出意外就是 0，如果不调用 set 方法的话
}

//再看看 RedisConfiguration 的 getDatabaseOrElse 方法
static Integer getDatabaseOrElse(@Nullable RedisConfiguration configuration, Supplier<Integer> other) {
    Assert.notNull(other, "Other must not be null!");
    return isDatabaseIndexAware(configuration) ? ((RedisConfiguration.WithDatabaseIndex)configuration).getDatabase() : (Integer)other.get();
}

//就是看 configuration 实现类的 getDatabase 方法，你会发现无论是哨兵或者集群，都直接/间接的取到了 int 类型的 database，所以默认不设置值 ，就是操作的 db0
// 集群模式
// RedisClusterConfiguration
// 哨兵模式
// RedisSentinelConfiguration
```

**1.如何操作其他的 db?**

自定义配置，继承对应的 configuration 实现类，重写 getDatabase 方法就好。如果理解够深，可以直接自己写一个 configuration 的实现。

或者在 JedisConnectionFactory 里面调用 setDatabase 覆盖默认配置

> 在切换 db 的时候，要注意不要切换到不存在的 db 上去了。



## 4.查看 redis db 信息

使用 info keyspace 可以查看数据库的 key 信息，

```shell
127.0.0.1:6379> info keyspace
# Keyspace
db0:keys=8654,expires=20,avg_ttl=24891505786

# 切换到第二个数据库
127.0.0.1:6379> select 1
OK
127.0.0.1:6379[1]> info keyspace
# Keyspace
db0:keys=8654,expires=20,avg_ttl=21066294826
127.0.0.1:6379[1]>
```

> 注意: 使用 info keyspace 查看的是所有数据库的 key 信息，切换到不同的数据库，获取的 key 信息相同。

## 5.查看 redis 内存信息

使用 info memory 可以查看数据库的内存信息。

```shell
127.0.0.1:6379> info memory
# Memory
used_memory:4850830296
used_memory_human:4.52G
used_memory_rss:5264429056
used_memory_peak:5309512376
used_memory_peak_human:4.94G
used_memory_lua:36864
mem_fragmentation_ratio:1.09
mem_allocator:jemalloc-3.6.0
```

> 直接输入 info 可以看到全部的配置信息

## Redis 和 memcached 的比较

- Redis 支持两种形式的将内存中的数据写入硬盘，memcached 不行。
- Redis 能支持除了 String 类型的字符串以外，还支持存储其他 4 种类型的数据结构，而 memcached 只能支持存储普通的字符串键。



## 一些缓存服务器的特性与功能

| 名称      | 类型                         | 数据存储选项                                                 | 查询类型                                                     | 附加功能                                                     |
| --------- | ---------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| Redis     | 使用内存存储的非关系型数据库 | 字符串，列表，集合，散列表，有序集合                         | 每种数据类型都有自己的命令，另外 Redis 还提供了批量操作和不完全的事务支持 | 发布与订阅，主从复制（master/slave replication），持久化，lua脚本（存储过程） |
| memcached | 使用内存存储的键值缓存       | 键值之间的映射关系                                           | 创建命令，读取命令，更新命令，删除命令以及其他的命令         | 为提升性能而设的多线程服务器                                 |
| mysql     | 关系型数据库                 | 每个数据库可以包含多个表，每个表可以包含多个行，可以处理多个表的视图，支持空间和第三方扩展 | select，update，delete，insert，数学函数，存储过程           | 支持 ACID 性质（需要使用 InnoDB 引擎），主从复制和主主复制（master/master replication） |
| mongodb   | 使用硬盘存储的非关系文档存储 | 每个数据库可以包含多个表，每个表可以包含多个无 schema 的 BSON 文档 | 创建命令，读取命令，更新命令，删除命令，条件查询命令         | 支持 map-reduce 操作，主从复制，分片，空间索引（spatial index） |

