## 1. 写在前面

所有笔记的 md 文件都存储在 github 上，但是由于没用图床的原因，github 上直接访问笔记可能图片会无法访问，所以我将每个笔记的连接地址都改成了我自己的博客地址，如果 github 上无法访问，可以直接去[我的博客](https://dailypaper.cn)访问。

关于算法部分，我在学习了之后，会尝试使用 java 代码将对应的算法实现一遍，如果有错误，欢迎指正。源码也在 github 上，如果有想参考的同学可以[点这里](https://github.com/selfassu/leetcode/tree/master/src/main/java/me/lqw/algorithm)。

笔者在写这些东西的时候，已经工作了 4 年了。前两年在深圳做的 Android 开发，后两年在武汉良品铺子做的 Java 开发，整理这些东西一来是有很多东西只是知其然不知其所以然，不想继续浑浑噩噩下去。二来只是想找一份薪水稍高，体面点的的工作（在武汉我这样的打工仔压力太大了）。

整理的东西有点多，难免会出现误差错误，还希望发现错误的师兄师姐不吝赐教，万分感谢。



> 源码部分的解析均是基于 jdk8



## 2. 这是啥

一份学习笔记，巩固知识。同时期望能帮我找到一份薪水高、体面的工作。

|     Part1     |    Part2    |    Part3    |    Part4 |     Part5     |    Part6 |    Part7    |    Part8 |  Part9 |     Part10     |
| :-------: | :------: | :-------: | :---------: | :-------: | :---------: | :-------: | :-----------: | :-------: | :-------: |
| java<br>📈 | web<br>🌐 | framework<br>⚔ |jvm<br>⚙ | concurrent<br>⛓ | database<br>🗄 | algorithm<br>🔐 | os<br>📺 | network<br>📡 | interview<br>📧 |

## 3. 目录

### 1. Java 基础部分

- [Java 中的八种基本数据类型，以及它们的封装类]()
- [switch 能否用 String 做参数]()
- [equals 与 == 的区别]()
- [Object 有哪些共用的方法]()
- [Java 的四种引用，强弱软虚，用到的场景](xxx)
- [HashCode 的作用](http://www.baidu.com)
- [ArrayList、LinkedList、Vector 的区别]()
- [String、StringBuffer 与 StringBuilder 的区别]()
- [Map、Set、List、Queue、Stack 的特点与用法]()
- [HashMap 和 ConcurrentHashMap 的区别，HashMap 的底层源码]()
- [TreeMap、HashMap、LinkedHashMap 的区别]()
- [Collection 包结构，与 Collections 的区别]()
- [try catch finally， try 里面有 return， finally 还执行吗]()
- [Exception 与 Error 包结构。OOM 你遇到过哪些情况， SOF 你遇到过哪些情况]()
- [Java 面向对象的三个特征与含义]()
- [Override 和 Overload 的含义与区别]()
- [interface 与 abstract 的区别]()
- [static class 和 non static class 的区别]()
- [Java 多态的实现原理]()
- ~~[实现多线程的两种方法：Thread 与 Runable]()~~ 见[线程池实现原理](https://dailypaper.cn/article/threadpool)（三种实现线程的方式）
  - 继承 Thread 类，实现 run 方法，**无**返回值
  - 实现 Runable 接口 ，实现 run 方法，**无**返回值
  - 实现 Callable 接口，实现 call 方法，**有**返回值 Future 类型
-  线程同步的方法：sychronized、lock、reentrantLock 等
-  锁的等级：方法锁、对象锁、类锁
-  写出生产者消费者模式
-  [ThreadLocal 的设计理念与作用](01-java 基础/ThreadLocal的原理和作用.md)
- 线程池的工作原理
  -  [线程池的工作原理，包含初始化参数，拒绝策略等，基于 1.8 的源码分析](https://dailypaper.cn/article/threadpool)
-  Concurrent 包中的其他东西： ArrayBlockingQueue，CountDownLatch 等
-  wait() 和 sleep() 的区别
-  foreach 与正常 for 循环效率对比
-  Java IO 与 NIO
- 反射的作用与原理
- [泛型常用特点，List< String > 能否转换成 List< Object >]()
-  解析 XML 的几种方式的原理与特点：DOM、SAX、PULL
-  Java 与 C++ 对比
-  Java1.7 与 1.8 新特性
- 常用的设计模式：单例、工厂、适配器、责任链、观察者等
- JNI ( Java Native Interface ) 的使用

------



### Java 部分内容源码分析（基于 jdk8）

- Map 接口典型实现类源码分析
  - [hashMap 源码分析之初始化，扩容时机](https://dailypaper.cn/article/hashmap-initial)
  - [HashMap 原理之存储结构](https://dailypaper.cn/article/hashmap-storage-structure)
  - HashMap 面试题
    - [准备使用 HashMap 存储 1w 条数据，构造方法传入 1w 会触发扩容吗？传入 1k 呢?](01-java 基础/准备用HashMap存储 1w 条数据，构造方法传入 1w 进去会触发扩容吗.md)
- List 接口典型实现类源码解析
- Set 接口典型实现类源码解析

### Java 多线程

- [线程池的工作原理](https://dailypaper.cn/article/threadpool)
  - 线程面试题
    - 如何使两个线程交叉执行打印 hello，线程 1 打印 h，线程 2 打印 e，线程 1 打印 l，线程 2 l，线程 1 打印 e
      - 利用对象的 wait 和 notify 方法





### Java 虚拟机

- 内存模式以及分区，需要详细到每个区放什么
- 堆里面的分区：Eden、survival from to、老年代各自的特点
- 对象的创建方法、对象的内存分配、对象的访问定位
- GC 的两种判定方法：引用计数与引用链
- GC 的三种手机方法：标记清除、标记整理、复制算法的原理与特点，分别用在什么地方，如果让你优化收集方法，有什么思路
- GC 收集器有哪些？ CMS 收集器与 G1 收集器的特点
- Minor GC 与 Full GC 分别在什么时候发生
- 几种常用的内存调试工具：jmap、jstack、jconsole
- 类加载的五个过程：加载，验证，准备，解析，初始化
- 双亲委派模型：Bootstrap ClassLoader、Extension ClassLoader、ApplicationClassLoader
- 分配：静态分派与动态分派

### 操作系统相关

- [进程和线程的区别](https://dailypaper.cn/article/process-and-thread)
- 死锁的必要条件，怎么处理死锁
- Window 内存管理方式：段存储、页存储、段页存储
- 进程的几种状态
- IPC 几种通信方式
- 什么是虚拟内存
- 虚拟地址、逻辑地址、线性地址、物理地址的区别

------

### 计算机网络相关

- OSI 与 TCP/IP 各层的结构与功能，都有那些协议
- TCP 与 UDP 的区别
- TCP 报文结构
- TCP 的三次握手与四次挥手过程，各个状态名称与含义， TIMEWAIT 的作用。
- TCP 拥塞控制
- TCP 滑动窗口与回退 N 帧协议
- Http 的报文结构
- Http 的状态码含义
- Http Request 的几种类型
- Http1.1 和 Http1.0 的区别
- Http 怎么处理长连接
- Cookie 与 Session 的作用与原理
- 电脑上访问一个网页，整个过程是怎样的：DNS、HTTP、TCP、OSPF、IP、ARP
- Ping 的整个过程，ICMP 报文是什么
- C/S 模式下使用 socket 通信，几个关键函数
- IP 地址分类
- 路由器与交换机区别

------

### 算法相关内容

- [链表与数组](https://dailypaper.cn/article/333)
- 队列和栈，出栈与入栈
- 链表的删除、插入、反向
- 字符串操作
- Hash 表的 hash 函数，冲突解决方法有哪些
- 各种排序：冒泡，选择，插入，希尔，归并，快排，堆排，桶排，基数的原理，平均时间复杂度，最坏时间复杂度、空间复杂度，是否稳定
  - [冒泡排序](https://dailypaper.cn/article/bubble-sort)
  - [选择排序](https://dailypaper.cn/article/select-sort)
- 快排的 partition 函数与归并的 Merge 函数
- 对冒泡与快排的改进
- 二分查找，与变种二分查找
  - [二分查找-本地文档，比较简单，没有放在博客上](07-算法/二分查找.md)
  - 变种二分查找
- 二叉树，B+ 树， AVL 树，红黑树、哈弗曼树
- 二叉树的前中后序遍历：递归与非递归写法，层序遍历算法
- 图的 BFS 与 DFS 算法，最小生成树 prim 算法与最短路径 Dijkstra 算法
- KMP 算法
- 排列组合问题
- 动态规划、贪心算法、分治算法
- 大数据处理：类似 10 亿条数据找出最大的 1000 个数

------

### 数据库相关内容

- Redis
  - [Redis 事务](https://dailypaper.cn/article/redis-transaction)
  - [Redis 持久化策略](https://dailypaper.cn/article/redis-snapshot-aof)
  - Redis 基本数据类型
  - Redis 主从同步，哨兵，集群模式

### 框架相关

- Spring&SpringBoot&SpringMVC
  - FactoryBean 和 BeanFactory 的区别
  - SpringMVC 执行流程
  - Spring 中使用了哪些设计模式
    - 工厂模式 BeanFactory
    - 代理模式，基于 Jdk 的动态代理和 cglib 代理
    - 观察者模式（事件和监听）
- Dubbo
- Mybatis

