## 关于 Kafka 分区

- 每个分区都是一个有序、不可变的消息序列，后续新来的消息会源源不断地、持续追加到分区的后面、这相当于一种结构话的提交日志（类似于 GIT 的提交日志）
- 分区中的每一条消息都会被分配一个连续的 id 值（即 offset），该值用于唯一标识分区中的每一条消息



## 分区的重要作用

- 分区中的消息数据是存储在日志文件中的，而且同一分区中的消息数据是按照发送顺序严格有序的。分区在逻辑上对应一个日志，当生产者将消息写入分区中时，实际上是写到了分区所对应的日志当中。而日志可以看做是一种逻辑上的概念，它对应于磁盘上的一个目录。一个日志文件由多个 Segment（段）来构成，每一个 Segment 对应于一个索引文件与一个日志文件。
- 借助于分区，我们可以实现 Kafka Server 的水平扩展。对于一台机器来说，无论是物理机还是虚拟机，其运行能力总归是有上限的。当一台到达器能力上限是就无法再扩展了，即垂直扩展能力总是要收到硬件制约的。通过使用分区，我们可以将一个主题中的消息分散到不同的 Kafka Server 上（这里需要使用到 Kafka 集群），这样当机器的能力不足时，我们只需要添加机器就可以了，在新的机器上创建新的分区，这样理论上就可以实现无限的水平扩展能力。
- 分区还可以实现并行处理能力，向一个主题所发送的消息会发送给该主题所拥有的不同的分区中，这样消息就可以实现并行发送与处理，由多个分区来接收所发送的消息。



## Segment（段）

一个分区（partition）是由一系列有序、不可变的消息所构成的。一个 partition 中的消息数量可能会非常多，因此显然不能将所有的消息都保存到一个文件当中。因此，类似于 log4j 的 rolling log，当 partition 中的消息数量增长到一定程度之后，消息文件会进行切割，新的消息会被写到一个新的文件当中，当新的文件增长到一定程度后，新的消息又会被写到另一个文件当中，以此类推：这一个个新的数据文件我们就称之为 segment（段）

因此，一个 partition 在物理上是由一个或者多个 segment 所构成的。每个 segment 中则保存了真实的消息数据。



## 关于 partition 与 segment 之间的关系

- 每个 partition 都相当于一个大型文件被分配到多个大小相等的 segment 数据文件中，每个 segment 中的消息数量未必相等（这与消息大小有紧密的关系，不同的消息所占据的磁盘空间显然是不一样的），这个特点使得老的 segment 文件可以很容易就被删除掉，有助于提升磁盘的利用效率。
- 每个 partition 只需要支持顺序读写即可，segment 文件的生命周期是由 KafkaServer 的配置参数所决定的。比如说 server.properties 文件中的参数项 log.retention.hours=168 就表示 7 天后删除日志文件。



## 关于分区中四个文件的含义与作用

- 00000000000000000000.index：它是 segment 文件的索引文件，它与接下来我们要介绍的 00000000000000000000.log 数据文件是成对出现的。后缀 .index 就表示这是个索引文件。
- 00000000000000000000.log：它是 segment 文件的数据文件，用于存储实际的消息。该文件格式是二进制的。segment 文件的命名规则是 partition 全局的第一个 segment 从 0 开始的，后续每个 segment 文件名为上一个 segment 文件最后一条消息的 offset 值。没有数字则用 0 来填充。由于这里的主题的消息数量较少，因此只有一个数据文件。
- 00000000000000000000.timeindex：该文件是一个基于消息日期的索引文件，主要用途是在一些根据日期或是时间来寻找消息的场景下使用，此外在基于时间的日志 rolling 或是基于时间的日志保留策略等情况下也会使用。实际上，该文件是在 kafka 较新的版本中才增加的，旧版本的 kafka 是没有该文件的，它是对 *.index 文件的一个有益补充。 *.index 文件是基于偏移量的索引文件，而 *.timeindex 则是基于时间戳的索引文件。
- leader-epoch-checkpoint：是 leader 的一个缓存文件。实际上，它是与 Kafka 的 HW（High Water）与 LEO（Long End Offset）相关的一个重要文件。



## kafka 指令

```shell
# 显示已有主题列表
kafka-topics.sh --list --zookeeper localhost:2181
# 创建主题
kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic myTopic
# 删除主题
kafka-topics.sh --delete --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic myTopic


kafka-topics.sh --describe --topic myTopic --zookeeper localhost:2181

# 开启生产者
kafka-console-producer.sh --broker-list localhost:9092 --topic myTopic

# 开启消费者
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic myTopic
## 从开始的地方开始获取消息
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic myTopic --from-beginning
## 指定消费者所位于的消费者组
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic myTopic --group myGroup 
## 不指定消费者组就是广播效果，指定消费者组就是单播效果
```

kafka 的 topic 信息都是在 zookeeper 上的

连接到 zookeeper 上，可以使用 ls  /config/topic 上 或者 ls2 /config/topic  上





























































