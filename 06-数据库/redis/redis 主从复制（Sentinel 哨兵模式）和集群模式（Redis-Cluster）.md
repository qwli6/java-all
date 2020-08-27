关系型数据库通常会使用一个主服务器（master）向多个从服务器（slave）发送更新，并使用从服务器来处理所有的读请求。



在需要扩展读请求的时候，或者需要写入临时数据的时候，用户可以通过设置额外的 redis 从服务器来保存数据集的副本，在接收到主服务器发送的数据初始副本之后，客户端每次想主服务器进行写入时，从服务器都会实时得到更新。



在部署好主从服务器之后，客户端就可以向任意一个从服务器发送读请求了，而不必像之前一样，所有的请求都发送给主服务器。



## Redis 复制选项配置

当从服务器连接上主服务器是，主服务器会执行 BGSAVE 操作。需要保证主服务器正确设置了 dir 和 dbfilename 选项，并且这两个选项所指示的路径和文件对于 Redis 进程来说都是可写的。



通过  slaveof host port 来执行属于哪台机器的从服务器

![image-20200826222708569](/Users/liqiwen/Library/Application Support/typora-user-images/image-20200826222708569.png)



在启动 redis 的时候，可以通过 slaveof host port 选项的配置来指定。如果指定了，那么 Redis 服务器将根据该选项给定的 host 和 port 来连接主服务器。



如果 redis 服务器正在运行，也可以通过发送 slaveof no one 命令来让你服务器终止复制。



## Redis 复制和启动过程

从服务在连接主服务器的时候，主服务器会创建一个快照文件并将其发送至从服务器。

| 步骤 | 主服务器操作                                                 | 从服务器操作                                                 |
| ---- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 1    | （等待命令进入）                                             | 连接（或者重连接）主服务器，发送 SYNC 命令                   |
| 2    | 执行 BGSAVE 生成快照过程，并使用缓冲区记录 BGSAVE 执行之后的所有写命令 | 根据配置选项来决定是否继续使用现有的数据（如果有的话）来处理客户端的命令请求，还是向看发送请求的客户端返回错误 |
| 3    | BGSAVE 执行完毕，向从服务器发送快照文件，并在发送期间继续使用缓冲区记录被执行的写命令 | 丢弃所有的旧数据，开始载入主服务器发来的快照文件             |
| 4    | 快照文件发送完毕，开始向从服务器发送存储在缓冲区里面的写命令 | 完成对快照文件的解释操作，向平常一样开始接受命令请求         |
| 5    | 缓冲区里面的写命令发送完毕；从发送完毕开始，每收到一个写命令，就向从服务器同步一次 | 执行主服务器发来的所有存储在缓冲区里面的写命令；并且从现在开始，接收并执行主服务器传来的每个写命令 |

从服务器的配置比较简单，

- 在配置项里面配置 SLAVEOF HOST PORT 来将一个 Redis 设置成一个从服务器，如果是配置项，则从服务器在启动的时候，会自动执行任何可用的快照或者 AOF 文件，执行完毕后执行上表的同步操作
- 如果是命令模式，在命令执行完毕后，会立即执行上表的同步操作

> 在执行数据同步时，从服务器会清空自己所有的数据，替换成从主服务器同步过来的所有数据。如果需要从服务器的数据，在设置成从服务器时记得备份。



## Redis 是否支持主主复制（master/master replication）

**Redis 不支持主主复制**。主主复制会导致数据混乱



虽然 Redis 不支持主主复制，但是却是支持主从链的，就像一个树状结构一样。

A 主服务器用有 A1 A2 A3 三个从服务器 A1 又拥有 B1 B2 B3 三个从服务器，这样的结构我们一般称之为**主从链**，这样可以将数据同步到多台 Redis 服务器上。





## 故障恢复

再多的 Redis 服务器也无法避免意外发生，如果发生了意外，我们就需要想办法去恢复数据文件。

**1.验证快照文件和 AOF 文件**

Redis 提供了两个命令来验证 AOF 文件的状态和快照文件的状态，并且在有需要的情况下对文件进行修改。

```shell
redis-check-aof
redis-check-dump

[root@nginx-19-60 22:50 /usr/local/src/redis/redis-3.0.2/utils]
# redis-check-aof
Usage: redis-check-aof [--fix] <file.aof>

[root@nginx-19-60 22:50 /usr/local/src/redis/redis-3.0.2/utils]
# redis-check-dump
Usage: redis-check-dump <dump.rdb>
```

如果用户在运行 redis-check-aof 指定了 --fixed 参数，redis 会对 aof 文件进行修复。修复的原理就是扫描 aof 文件，当发现第一个错误或者不完整的命令时，将后面的指令直接删除，只保留出错之前的命令。



**从服务器客户端一般无法对其进行写入**

如果主服务器（A）宕机了，需要更换成另外一台（C），有两种解决方案

- 在从服务器（B）上发送 SAVE 命令，生成快照文件。将快照文件发送至要更新的主服务器（C），启动主服务器（C），设置 C 为 B 的主服务器即可
- 让 B 成为主服务器，C 成为 B 的从服务器

如果让 B 或者 C 成为主服务器了，那么客户端也需要同步更新配置。





## Redis pipeline 

流水线方式执行，可设置参数 boolean 类型

- true，将多条命令包裹在 multi 和 exec 里面执行
- false，将多条命令不包裹在 multi 和 exec 里面执行

```java
//通过 redisTemplate 执行 pipeline
redisTemplate.executePipelined(new RedisCallback<String>() {
 	@Override
 	public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
 	redisConnection.openPipeline();
 		//redisConnection.
 		return null;
 	}
});
```

使用 pipeline 模式可以减少 redis 往返通信的时间，它会先将命令缓存起来，统一打包发送给 Redis 服务器，减少通信次数。



## 使用 Redis-benchmark 检测 redis 性能

```shell
# redis-benchmark -c 1 -q
```

![image-20200826231635238](/Users/liqiwen/Library/Application Support/typora-user-images/image-20200826231635238.png)

