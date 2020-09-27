# 一、Notices

> **源码部分的解析基于 ` jdk8`**

一份学习笔记，巩固知识。同时期望能帮我找到一份薪水高、体面的工作。



> 部分内容在[博客](https://dailypaper.cn)上，还有一部分内容在 [wiki](https://github.com/selfassu/java-all/wiki) 里面



# 二、Contents

## Java 基础

1. [Java 中的八种基本数据类型，以及它们的封装类]()
2. [switch 能否用 String 做参数]()
3. [equals 与 == 的区别]()
4. [Object 有哪些共用的方法](https://github.com/selfassu/java-all/wiki/Object%E7%B1%BB%E4%B8%8B%E9%9D%A2%E7%9A%84%E5%87%A0%E7%A7%8D%E6%96%B9%E6%B3%95)
5. [Java 的四种引用，强弱软虚，用到的场景](xxx)
6. [HashCode 的作用](http://www.baidu.com)
7. [ArrayList、LinkedList、Vector 的区别]()
8. [String、StringBuffer 与 StringBuilder 的区别]()
9. [Map、Set、List、Queue、Stack 的特点与用法]()
10. [HashMap 和 ConcurrentHashMap 的区别，HashMap 的底层源码]()
11. [TreeMap、HashMap、LinkedHashMap 的区别]()
12. [Collection 包结构，与 Collections 的区别]()
13. [try catch finally， try 里面有 return， finally 还执行吗]()
14. [Exception 与 Error 包结构。OOM 你遇到过哪些情况， SOF 你遇到过哪些情况]()
15. [Java 面向对象的三个特征与含义]()
16. [Override 和 Overload 的含义与区别]()
17. [interface 与 abstract 的区别]()
18. [static class 和 non static class 的区别]()
19. [Java 多态的实现原理]()
20. 线程同步的方法：sychronized、lock、reentrantLock 等
21. 锁的等级：方法锁、对象锁、类锁
22. 写出生产者消费者模式
23. Concurrent 包中的其他东西： ArrayBlockingQueue，CountDownLatch 等
24. wait() 和 sleep() 的区别
25. foreach 与正常 for 循环效率对比
26. Java IO 与 NIO
27. 反射的作用与原理
28. [泛型常用特点，List< String > 能否转换成 List< Object >]()
29. 解析 XML 的几种方式的原理与特点：DOM、SAX、PULL
30. Java 与 C++ 对比
31. Java1.7 与 1.8 新特性
32. 常用的设计模式：单例、工厂、适配器、责任链、观察者等
33. JNI ( Java Native Interface ) 的使用



## Java 集合源码分析

> 基于 jdk 1.8 分析

1. Map 接口典型实现类源码分析
   1. [HashMap 源码分析之初始化、扩容时机](https://dailypaper.cn/article/hashmap-initial)
   2. [HashMap 原理之存储结构](https://dailypaper.cn/article/hashmap-storage-structure)

2. List 接口典型实现类源码解析
   1. ArrayList 源码解析
   2. LinkedList 源码解析

3. Set 接口典型实现类源码解析
   1. HashSet 源码解析
   2. Hashtable 源码解析



## Java 多线程

1. [线程池的工作原理](https://dailypaper.cn/article/threadpool)

2. 线程池的阻塞队列

3. [ThreadLocal 的设计理念与作用](01-java基础/ThreadLocal的原理和作用.md)

4. ~~[创建线程的两种方法：Thread 与 Runable]()~~ 见[线程池实现原理](https://dailypaper.cn/article/threadpool)（三种实现线程的方式）

   1. 继承 Thread 类，实现 run 方法，**无**返回值

      ```java
      public class ThreadDemo1 extends Thread {
          @Override
          public void run() {
              super.run();
              System.out.println("当前线程名称:" + this.getName());
          }
      }
      ```

   2. 实现 Runable 接口 ，实现 run 方法，**无**返回值

      ```java
      public class ThreadDemo2 implements Runnable {
          @Override
          public void run() {
              System.out.println("第二种方式实现线程:" + Thread.currentThread().getName());
          }
      }
      ```

   3. 实现 Callable 接口，实现 call 方法，**有**返回值 Future 类型

      ```java
      public class ThreadDemo3 implements Callable<String> {
          @Override
          public String call() throws Exception {
              System.out.println("第三种线程名称：" + Thread.currentThread().getName());
              return "success";
          }
      }
      ```



## Java 虚拟机

1. [JVM 内存管理](https://github.com/selfassu/java-all/wiki/JVM-%E5%86%85%E5%AD%98%E7%AE%A1%E7%90%86)
2. 内存模式以及分区，需要详细到每个区放什么
3. 堆里面的分区：Eden、survival from to、老年代各自的特点
4. 对象的创建方法、对象的内存分配、对象的访问定位
5. GC 的两种判定方法：引用计数与引用链
6. [GC 的三种收集方法：标记清除、标记整理、复制算法的原理与特点？分别用在什么地方？如果让你优化收集方法，有什么思路？](https://github.com/selfassu/java-all/wiki/GC-%E7%9A%84%E4%B8%89%E7%A7%8D%E6%94%B6%E9%9B%86%E6%96%B9%E6%B3%95%EF%BC%9A%E6%A0%87%E8%AE%B0%E6%B8%85%E9%99%A4%E3%80%81%E6%A0%87%E8%AE%B0%E6%95%B4%E7%90%86%E3%80%81%E5%A4%8D%E5%88%B6%E7%AE%97%E6%B3%95%E7%9A%84%E5%8E%9F%E7%90%86%E4%B8%8E%E7%89%B9%E7%82%B9%EF%BC%9F)
7. GC 收集器有哪些？ CMS 收集器与 G1 收集器的特点。
8. Minor GC 与 Full GC 分别在什么时候发生？
9. [几种常用的内存调试工具：jmap、jstack、jconsole](https://dailypaper.cn/article/337)
10. 类加载的五个过程：加载、验证、准备、解析、初始化
11. 双亲委派模型：Bootstrap ClassLoader、Extension ClassLoader、ApplicationClassLoader
12. 分配：静态分派与动态分派



## 操作系统

1. [进程和线程的区别](https://dailypaper.cn/article/process-and-thread)
2. 死锁的必要条件，怎么处理死锁
3. Window 内存管理方式：段存储、页存储、段页存储
4. 进程的几种状态
5. IPC 几种通信方式
6. 什么是虚拟内存
7. 虚拟地址、逻辑地址、线性地址、物理地址的区别



## 计算机网络

1. OSI 与 TCP/IP 各层的结构与功能，都有那些协议
2. TCP 与 UDP 的区别
3. TCP 报文结构
4. TCP 的三次握手与四次挥手过程，各个状态名称与含义， TIMEWAIT 的作用
5. TCP 拥塞控制
6. TCP 滑动窗口与回退 N 帧协议
7. Http 的报文结构
8. Http 的状态码含义
9. Http Request 的几种类型
10. Http1.1 和 Http1.0 的区别
11. Http 怎么处理长连接
12. Cookie 与 Session 的作用与原理
13. 电脑上访问一个网页，整个过程是怎样的：DNS、HTTP、TCP、OSPF、IP、ARP
14. Ping 的整个过程，ICMP 报文是什么
15. C/S 模式下使用 socket 通信，几个关键函数
16. IP 地址分类
17. 路由器与交换机区别

## 算法&数据结构

1. [链表与数组](https://dailypaper.cn/article/333)
2. 对列和栈，出栈与入栈
3. 链表的删除、插入、反向
4. 字符串操作
5. Hash 表的 hash 函数，冲突解决方法有哪些
6. 各种排序：冒泡、选择、插入、希尔、归并、快排、堆排、桶排、基数的原理。平均时间复杂度、最坏时间复杂度、空间复杂度以及是否稳定
   1. [冒泡排序](https://dailypaper.cn/article/bubble-sort)
   2. [选择排序](https://dailypaper.cn/article/select-sort)

7. 快排的 partition 函数与归并的 Merge 函数
8. 对冒泡与快排的改进
9. 二分查找，与变种二分查找
   1. [二分查找-本地文档，比较简单，没有放在博客上](07-算法/二分查找.md)
   2. 变种二分查找
10. 二叉树、B+ 树、 AVL 树、红黑树、哈弗曼树
11. **二叉树遍历**
    1. 前序遍历
    2. 中序遍历
    3. 后序遍历
12. 二叉树的递归与非递归写法，层序遍历算法
13. 图的 BFS（广度优先） 与 DFS（深度优先） 算法，最小生成树 prim 算法与最短路径 Dijkstra（单源点最短路径） 算法
14. KMP 算法
15. 排列组合问题
16. 动态规划、贪心算法、分治算法
17. 大数据处理：类似 10 亿条数据找出最大的 1000 个数



## 数据库

1. Redis
   1. [Redis 事务](https://dailypaper.cn/article/redis-transaction)
   2. [Redis 持久化策略](https://dailypaper.cn/article/redis-snapshot-aof)
   3. [Redis 基本数据类型](06-数据库/redis/redis 的基本概念.md)
   4. [Redis 主从同步，哨兵，集群模式](06-数据库/redis/redis 主从复制（Sentinel 哨兵模式）和集群模式（Redis-Cluster）.md)

2. Mongodb

3. Mysql



## Web 框架

1. Spring &SpringBoot&SpringMVC
   1. FactoryBean 和 BeanFactory 的区别
   2. SpringMVC 执行流程
   3. Spring 中使用了哪些设计模式
      1. 工厂模式 BeanFactory
      2. 简单工厂模式（静态工厂方法） BeanFactory
      3. 代理模式，基于 Jdk 的动态代理和 cglib 代理（AOP）
      4. 观察者模式（事件和监听 ApplicationListener）
      5. 单例模式
      6. 模板方法模式（xxxTemplate）
      7. 适配器模式（Adapter）

2. SpringCloud

3. Dubbo

4. Mybatis

5. Zookeeper

6. Nginx

7. Nacos



## 面试题

**1. 线程**

1. 如何使两个线程交叉打印？例如打印 hello。线程 1 打印 h，线程 2 打印 e，线程 1 打印 l，线程 2 打印 l，线程 1 打印 e。
   1. 解决方案参考：Object 对象的 wait 和 notify 方法



**2. 集合**

1. 准备使用 HashMap 存储 1w 条数据，构造方法传入 1w 会触发扩容吗？传入 1k 呢?
   1. 解决方案参考[这里](https://github.com/selfassu/java-all/wiki/%E5%87%86%E5%A4%87%E7%94%A8-HashMap-%E5%AD%98%E5%82%A8-1w-%E6%9D%A1%E6%95%B0%E6%8D%AE%EF%BC%8C%E6%9E%84%E9%80%A0%E6%96%B9%E6%B3%95%E4%BC%A0%E5%85%A5-1w-%E8%BF%9B%E5%8E%BB%E4%BC%9A%E8%A7%A6%E5%8F%91%E6%89%A9%E5%AE%B9%E5%90%97%EF%BC%9F)