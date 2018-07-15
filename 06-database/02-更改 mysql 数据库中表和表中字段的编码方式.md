建表的时候，偶尔会不注意，把数据库中的表的编码方式搞成默认的了，这样会导致当我们想向数据库中插入中文字段的时候，会报错！



最简单粗暴的方式就是删了数据库表重新创建，这样解决最简单。但是在很多情况下是不允许我们对表进行删除操作的，所以说这种做法是不可取的。



既然删表重新来过不可取，那就只能通过指令来更改数据库的表的编码格式了！



### 更改 mysql 表的编码结构

```mysql
// 格式
alter table <tableName> default character set utf8; 

// 实际操作
alter table jblog_follow default character set utf8;
```



只是这样更改，你会发现还是插入不了中文，因为你只是改了表的编码，表中的字段编码也一样要更改成 `utf8`



### 查看表中的编码结构

```mysql
// 格式
show create table <tableName>
// 实际操作
show create table score;
```



然后把表中字段的编码也更改一下即可愉快的插入中文了。

### 更改表中字段的编码结构

```mysql
// 格式
alter table <tableName> convert to character set utf8;
// 实际操作
alter table `jblog_follow` convert to character set utf8;
```



