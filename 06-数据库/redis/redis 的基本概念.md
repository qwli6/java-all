## redis 概述

redis 是一个非常快的，基于内存的，以 key value 的形式存储，并且能存储 5 种不同数据类型的非关系型的数据库（NoSql）。

redis 键的数值类型必须为字符串，可存储的值有多种形式：

- 字符串
- 列表
- 集合
- 散列表
- 有序集合



redis 同时支持多种特性，比如将内存中的数据持久化到磁盘上，使用复制来扩展读性能，使用分片来扩展写性能。



## 连接远程 redis 服务

redis 默认端口号为 **6379**，可通过几种方式连接 redis

**1.通过 redis-cli 连接**

```shell
# 无认证操作
> redis-cli -h 127.0.0.1 -p 6379
127.0.0.1:6379> 

# 有认证操作
redis-cli -h 127.0.0.1 -p 6379 -a 密码
127.0.0.1:6379>
```

**2.通过 rdm 连接**

rdm 是一个可视化的 redis 连接工具，具体连接跟操作 navicat 连接 mysql 一样，这里不再赘述。



**3.远程停止 redis 服务**

```shell
redis-cli -h 127.0.0.1 -p6379 shutdown
```



## Redis 可操作的数据类型

| 可操作的数据类型 | 存储的值                       | 操作                                                         |
| ---------------- | ------------------------------ | ------------------------------------------------------------ |
| STRING（字符串） | 字符串类型，整数或者浮点型数据 | 对字符串或者字符换里面的部分内容做操作。对整型或者浮点型数值做自增或者自减操作 |
| LIST（列表）     | 列表                           | 从两端入队或者出队元素，或者只保留某个范围内的元素           |
| SET（无序集合）  | 无序集合                       | 添加，删除，或者获取单个元素。检查元素是否在集合中。计算交集，并集，差集。从集合中随机获取元素。 |
| HASH（哈希表）   | 包含键值对的无序散列表         | 添加，删除，或者获取单个键值对。获取所有键值对，检查某个键是否存在 |
| ZSET（有序集合） | 有序集合（sorted set）         | 添加，删除，或者获取元素，根据分值范围或者成员来获取元素。计算键的排名 |

**1.String**

通常我们最常使用的就是 String，下面将一下如何操作一个 String 类型的数值。

<p style='color: red; font-weight:bold;'>String 类型一个键值最大能存储 512MB</p>

```shell
# 设置一个值
> set hello world
OK
# 获取一个值
> get hello 
world
# 删除一个值
> del hello 
1
# 获取一个值
> get hello
NULL
```

自增数值

```shell
> set id 1
OK
# 将 id 增 1
> incr id
2
# 将 id 增 1
> incr id
3
# 将 id 减 1
> decr id
2
```



**2.List**

List 是简单的字符串列表，**按照插入的顺序进行排序**， 可以添加一个元素到列表的左边或者右边

<p style='color: red; font-weight: bold;'>列表最多能存储 2<sup>32</sup>-1 个元素</p>

```shell
# push 一个值, 向 test-list 中 push 一个值 a，从右侧压入
> rpush test-list a
(Integer)1
# push 一个值，向 test-list 中 push 一个值 b，从右侧压入
> rpush test-list b
(Integer)1
# 向 test-list 中 push 多个值，从右侧压入
> rpush test-list c d e f
(Integer)4

# 出队一个元素，从左侧弹出
> lpop test-list
"a"
# 获取范围某个范围内的值，从左侧开始获取
> lrange test-list 1 2
1) "c"
2) "d"

# lindex，从左侧开始，获取索引位为 x 的值
> lindex test-list 1
1) "a"
# 如果索引位无值，则返回 （nil）
> lindex test-list 100
(nil)
```

注意：这里的 **r**push 中的 **r** 表示向右入队一个值，**l**pop 中的 **l** 表示向左出队一个值。

同理 lrange 是**向左开始，取 1 到 2 之间的值**



> 1. 只有入队 push 和出队有左右之分，lrange 和 lindex 均无右侧操作
> 2. range 是取某一个区间范围内的值，尽量保证 key1 <= key2 
> 3. key1 和 key2 都只能为整数，如果出现了浮点型的小数，则取值 redis 会报错
> 4. 如果 key2 小于 key1，将返回一个空的集合

```shell
# 获取索引位从 0 开始，往后 100 个数值
> lrange list-test 0 100
1) "1"
2) "e"
3) "b"
4) "c"
	
# 获取索引未从 1 开始后面所有的值
> lrange list-test 1 -1
1) "e"
2) "b"
3) "c"

# 获取索引位从 1 开始，往后数一个的值
> lrange list-test 1 1
1) "e"

> lrange list-test 2 1
(empty list or set)

# 负数表示倒数，这条命令就是倒数第三个元素到倒数第二个元素之间的取值
> lrange list-test -3 -2
1) "e"
2) "b"

# key2 < key1, 则返回一个空的集合
> lrange list-test -3 -100
(empty list or set)
```

同样的 lpush，rpop 同理，**可以将 List 类型的数据理解成一个队列，只不过这个队列不仅仅满足于 FIFO 原则，两侧都可以压入，两侧都可以压出。**



**3.Set 集合**

Redis 的 Set 是 String 类型的集合，它是无序的。集合通过 Hash 表来实现的，所以时间复杂度和空间复杂度都为 O(1)

<p style='color: red; font-weight: bold'>集合中最多能存储 2<sup>32</sup>-1 个值</p>

```shell
# 向集合中添加一个 key1
127.0.0.1:6379> sadd setTest key1
(integer) 1
# 再次向集合中添加一个 key1
127.0.0.1:6379> sadd setTest key1
(integer) 0
# key1 已经存在了，所以返回的为 0
127.0.0.1:6379> sadd setTest key1
(integer) 0
127.0.0.1:6379> sadd setTest key2
(integer) 1
127.0.0.1:6379> sadd setTest key3
(integer) 1

# 取出所有的值
127.0.0.1:6379> smembers setTest
1) "key2"
2) "key1"
3) "key3"

# 注意返回的顺序，是无序的

# sismember 可以检测 setTest 这个集合中是否存在某一个 key
# 存在
127.0.0.1:6379> sismember setTest key1
(integer) 1
# 不存在
127.0.0.1:6379> sismember setTest key0
(integer) 0

# srem 可以从 set 集合中删掉某一个值
127.0.0.1:6379> srem setTest key0
(integer) 0
# 删掉 key1
127.0.0.1:6379> srem setTest key1
(integer) 1

# 去除 set 集合中的某一个值
127.0.0.1:6379> sismember setTest key1
(integer) 0
127.0.0.1:6379> smembers setTest
1) "key2"
2) "key3"
```

综上：

> 1. sadd key value... 是表示向 key 这个集合中添加一个（多个） value 值，如果 value 值存在，则不再添加
> 2. smembers key 是获取 key 这个集合中的全部值
> 3. sismember key value 是判断 value 这个值在 key 这个集合中是否存在
> 4. srem key value 是删除 key 这个集合中的 value 值
> 5. **set 集合是无序的列表**



**4.hash表**

Redis hash 是存储一个 key value 对的集合，比较适合对象存储。

<p style='color: red; font-weight: bold;'>每个 hash 可以存储 2<sup>32</sup>-1个键值对</p>

```shell
# 设置 key 为 hello，value 为 field1 -> hello, field2 -> world 的键值对
127.0.0.1:6379> hmset hello field1 "hello" field2 "world"
OK
```

数据展现形式如下

| row  | key    | value |
| ---- | ------ | ----- |
| 1    | field1 | hello |
| 2    | field2 | world |

![image-20200825140243049](/Users/liqiwen/Library/Application Support/typora-user-images/image-20200825140243049.png)

其实 hash 可以理解成 Java 中的 map 对象，redis 里面缓存的 key 为 hello，值却是一个 Map 对象，这个 Map 对象里面有两对 key value 形式的数据。

```shell
# 删除数据, 删除 key 为 hello 中的键为 field1 的键值对
127.0.0.1:6379> hdel hello field1
(integer) 1

# 获取数据
127.0.0.1:6379> hget hello field1
"hello"

# 获取全部数据
127.0.0.1:6379> hmget hello field1 field2 field3
1) "hello"
2) "world"
3) (nil)
```

> 注意：
>
> - hget 只能获取 hash 表单个 key 字段的数据，如果要获取 hash 表全部的字段数据，请使用 hmget，hmget 支持多参数查询
> - hmget 支持多参数查询，当出现不存在的 key 字段时，会返回 nil
> - hset 只能设置 hash 表单个 key 字段的数据，如果要支持设置多个字段数据，请使用 hmset，hmset 支持多参数设置值



使用 hset，如果设置了存在的数据，hset 会默认返回 0，如果返回了 1，则是插入了一个新的字段。

```shell
127.0.0.1:6379> hmget hello field1 field2 field3
1) "hello"
2) "world"
3) (nil)
# 设置不存在的数据
127.0.0.1:6379> hset hello field3 helloworld
(integer) 1
# 设置存在的数据
127.0.0.1:6379> hset hello field3 helloworld
(integer) 0
```

如果是用 hmset 设置了存在的数据， 不管是否重复，都会返回 OK，告诉当前设置状态。

```shell
# 多个有部分重复，则返回 OK
127.0.0.1:6379> hmset hello field3 helloworld field4 worldhello
OK
127.0.0.1:6379> hmget hello field1 field2 field3 field4
1) "hello"
2) "world"
3) "helloworld"
4) "worldhello"

# 单个重复
127.0.0.1:6379> hmset hello field3 helloworld
OK
```

hgetall 默认返回了 hash 集合中所有的字段和值。返回值中每个字段的下一个就是该字段的值，所以返回值的长度是哈希集合大小的两倍。

```shell
127.0.0.1:6379> hgetall hello
1) "field1"
2) "hello"
3) "field2"
4) "world"
5) "field3"
6) "helloworld"
7) "field4"
8) "worldhello"
```



**5.zset(有序集合 sorted set)**

redis 中的 set 和 zsert 本质上来说是一致的，都是一个 set 集合，并且不能有重复的元素。

但是有区别的是：**zset 是一个有序的集合，内部会为每一个元素关联一个 double 类型的分数。redis 正是通过分数来为集合中的成员进行由大到小的排序**。

存储结构：

| row  | value   | score |
| ---- | ------- | ----- |
| 1    | member  | 0     |
| 2    | member1 | 1     |
| 3    | member2 | 2     |

```shell
# 为 key hello 添加一个元素 member 分数为 1
127.0.0.1:6379> zadd hello 1 member
(integer) 1
# 为 key hello 添加一个元素 member1 分数为 2
127.0.0.1:6379> zadd hello 2 member1
(integer) 1

# range 还是范围取值，取 0 ~ 1 索引位的值
127.0.0.1:6379> zrange hello 0 1
1) "member"
2) "member1"

# 按分数排序取值，取 0 ~ 100 分数的 key
127.0.0.1:6379> zrangebyscore hello 0 100
1) "member"
2) "member1"
3) "member2"
```

如果命令 zrange， zrangebyscore 后面追加 withscores，那么会将每个 value 对应的 value 也展示出来。

```shell
127.0.0.1:6379> zrangebyscore hello 0 1 withscores
1) "member"
2) "0"
3) "member1"
4) "1"
```

## 其他 command 的解释[^1][^2]

上面的一些命令我们没有设置过期时间，所以 key 在 redis 中会永久存在。对于部分数据，我们期望它在固定的时间失效，所以需要给某一些 key 设置一下过期时间。

```shell
# hello 这个 key 会在 60s 内过期
127.0.0.1:6379> expire hello 60
(integer) 1
```

![image-20200825165032262](/Users/liqiwen/Library/Application Support/typora-user-images/image-20200825165032262.png)



检查 key 是否存在

```shell
127.0.0.1:6379> exists hello
(integer) 0
```



上面说了一些常用的命令操作，还有一些不常用的，可以自行去查看文档

[^1]:[redis 中文网](http://www.redis.cn/commands.html)
[^2]:[redis 官网](https://redis.io/commands)