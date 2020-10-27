- MySQL 原子性和持久性怎么保证？

  - A（原子性）
    - 原子性实现原理 undo log
    - 持久性 redo log
      - 和 undo log 相反，Redo log 记录的是新数据的备份。在事务提交前，只要将 redo log 持久化即可，不需要将数据持久化。当系统崩溃时，虽然数据没有持久化，但是 redo log 已经持久化。系统可以根据 redo log 的内容，将所有数据恢复到最新的状态。
  - C（一致性）
  - I（隔离性）
  - D（持久性）

- innodb 和 myisam 区别

  不同的存储引擎，不同的数据组织。

  |                  | MyISAM      | InnoDB                      |
  | ---------------- | ----------- | --------------------------- |
  | 索引类型         | 非聚簇索引  | 聚簇索引                    |
  | 支持事务         | 否          | 是                          |
  | 支持表锁         | 是          | 是                          |
  | 支持行锁         | 否          | 是                          |
  | 支持外键         | 否          | 是                          |
  | 支持全文索引     | 是          | 是（5.6 后支持）            |
  | 适合操作数据类型 | 大量 select | 大量 insert、delete、update |

  - innodb 有事务，myisam 没有事务
  - innodb 支持表锁和行锁，myisam 只支持表锁

- 索引分类

- innodb 的底层数据结构

- 为什么底层使用 B+树而不是用 B 树





```shell
# 查看连接进程
show processlist
```

数据库有三种日志：

1. binlog  -> 主从复制会用到
2. redolog
3. undolog
   1. 基于 InnoDB 存储引擎的，它是一种逻辑日志，可以如下理解：
      1. 当 delete 一条语句时，undo log 中会记录一条回滚的 insert 语句
      2. 当 insert 一条语句时，undo log 中会记录一条回滚的 delete 语句
      3. 当 update 一条语句时，undo log 中会记录一条相反更新的 update 语句

































































