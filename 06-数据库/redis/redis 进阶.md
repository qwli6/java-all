## 优化 Redis 内存

**1.短结构**

- 减少列表，集合，散列和有序集合的体积可以减少内存占用。

- 键名保持简短，也能节省空间

具体的压缩配置

```properties
# Hashes are encoded using a memory efficient data structure when they have a
# small number of entries, and the biggest entry does not exceed a given
# threshold. These thresholds can be configured using the following directives.
hash-max-ziplist-entries 512
hash-max-ziplist-value 64

# Lists are also encoded in a special way to save a lot of space.
# The number of entries allowed per internal list node can be specified
# as a fixed maximum size or a maximum number of elements.
# For a fixed maximum size, use -5 through -1, meaning:
# -5: max size: 64 Kb  <-- not recommended for normal workloads
# -4: max size: 32 Kb  <-- not recommended
# -3: max size: 16 Kb  <-- probably not recommended
# -2: max size: 8 Kb   <-- good
# -1: max size: 4 Kb   <-- good
# Positive numbers mean store up to _exactly_ that number of elements
# per list node.
# The highest performing option is usually -2 (8 Kb size) or -1 (4 Kb size),
# but if your use case is unique, adjust the settings as necessary.
list-max-ziplist-size -2

# Similarly to hashes and lists, sorted sets are also specially encoded in
# order to save a lot of space. This encoding is only used when the length and
# elements of a sorted set are below the following limits:
zset-max-ziplist-entries 128
zset-max-ziplist-value 64
```



长压缩列表和大整数集合也会带来性能问题，当结构突破了用户为压缩列表或者整数集合的设置的限制条件时，Redis 会将期自动转换成更为典型的数据结构。



**2.分片结构 shardid**

通常情况下，我们使用 Redis 的键值存储多是将值 Y 存储到键 X 里面，但是对于分片结构，值 Y 将会存储到键 X:<shardid> 里面。

对于散列表进行分片来说，因为散列表本身就存储着一些键，所以程序在对键进行划分的时候，可以把散列存储的键用作其中的一个信息源，并使用散列函数为键计算出一个数字的散列值。然后程序会根据需要存储的键的总数量以及每个分片需要存储的键数量，计算出所需要的分片数量，并使用这个分片数量和键的散列值来决定应该把键存储到哪一个分片里面。

对于数字类型的键，假设他们是连续的，那么程序会基于数字键本身的数值来只拍分片 ID，从而保证数值相似的键可以被存储到同一个分片里面。



**3.打包存储二进制位和字节**





## 扩展 Redis 性能

**1.提升读能力**

用户可以运行一些额外的服务器作为从服务器，然后接收到主服务器发送的数据副本并通过网络进行准实时的更新。通过将请求分散到不同的从服务器上进行处理，获得额外的读查询处理能力。

> 记住: **只对主服务器进行写入。** 在使用只读从服务器的时候，务必要记得只对 Redis 主服务器进行写入。在默认情况下，尝试对一个被配置成从服务器的 Redis 服务器进行写入，将引发一个错误（就算该从服务器是其他的从服务器的主服务器，也是如此。）**这一配置可以开启，但是一般都是默认关闭的。**



![哨兵模式](/Users/liqiwen/Library/Application Support/typora-user-images/image-20200827003902566.png)

**故障转移：**Redis 提供了一个 Redis sentinel 功能，它是配合 Redis 的复制功能一起使用，并对下线的主服务器进行故障转移。Redis sentinel 是运行在特殊模式下的 Redis 服务器，但它的行为和一般的 Redis 服务器不同。Sentinel 会监视一系列主服务器以及这些主服务器的从服务器，通过向主服务器发送 PUBLISH 命令和 SUBSCRIBE 命令，并向主服务器和从服务器发送 PING 命令，各个 Sentinel 进程可以自主识别可用的从服务器和其他的 Sentinel。当主服务器失效时，监视这个主服务器的所有 Sentinel 就会基于彼此共有的信息选出一个 Sentinel，并从现有的从服务器中选出一个新的主服务器。

当被选中的从服务器转换成主服务器后，那个被选中的 Sentinel 就会让剩余的其他从服务器去复制这个新的主服务器（在默认设置下，Sentinel 会一个接一个的迁移从服务器，但这个数量可以通过配置来进行修改）

除此之外，Sentinel 还提供了可选的故障通知功能没这个功能可以通过调用用户提供的脚本来执行配置更新等操作



**2.扩展写性能**

使用分片将消息扩展到多个 Redis 服务器上，或者将消息分片扩展到 Redis 多个数据库上。





## 优化 Redis 内存

先使用 info memory 查看 Redis 内存

```shell
127.0.0.1:6379> info memory
# Memory 
used_memory:14771466416   #占用内存
used_memory_human:13.76G  # 可读方式占用内存
used_memory_rss:17022504960  # 总内存
used_memory_rss_human:15.85G # 操作系统的总内存
used_memory_peak:18683106760 # 占用内存最大值
used_memory_peak_human:17.40G # 可读方式展示内存使用的最大值
used_memory_lua:155648 # lua 引擎耗费的内存
used_memory_lua_human:152.00K # lua 可读方式
mem_fragmentation_ratio:1.06 # used_memory_rss/used_memory 比例，内存碎片率
mem_allocator:jemalloc-3.6.0 # 内存分配器，默认是 jemalloc
```

![image-20200827110423052](/Users/liqiwen/Library/Application Support/typora-user-images/image-20200827110423052.png)



**1.内存管理**

- 内存上限（maxmemory）

  - 缓存应用内的内存回收机制触发 + 防止物理内存用尽（默认 redis 配置是无限制使用服务器内存）+ 服务器节点内存（单服务器上部署多个 redis 服务节点）

  在进行动态分配内存时要考虑内存碎片占用影响

  动态调整，扩展 redis 服务节点可用内存：config set maxmemory {}

- 内存回收

  - 主要参考回收机制：键过期，内存使用达到上限

  - 内存溢出控制，当内存达到最大 maxmemory，就会触发内存回收机制，具体策略根据 maxmemory-policy 配置来执行

    - noevication：默认不回收，达到内存上限，则不再接受写操作，并返回错误。
    - volatile-lru：根据 LRU 算法删除设置了过期时间的键，如果没有则不执行回收。
    - allkeys-lru：根据 LRU 算法删除键，针对所有键。
    - allkeys-random：随机删除键。
    - volatitle-random：速记删除设置了过期时间的键。
    - volatilte-ttl：根据键 ttl，删除最近过期的键，同样如果没有设置过期的键，则不执行删除。

    线上配置

    ![image-20200827111433504](/Users/liqiwen/Library/Application Support/typora-user-images/image-20200827111433504.png)

动态配置内存回收策略：config set maxmemory-policy {}



在设置了 maxmemory 的情况下，每次 redis 的操作都会检查执行内存回收，对于线上的环境，要保证 maxmemory > used_memory