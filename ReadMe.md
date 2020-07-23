# LEARN NOTES FOR INTERVIEW


## HOWTO?


## WHATIS?

> Learning plan for interview
>
> Learning content mainly comes from books, source code and official document interpretation.

|     Part1     |    Part2    |    Part3    |    Part4 |     Part5     |    Part6 |    Part7    |    Part8 |  Part9 |     Part10     |
| :-------: | :------: | :-------: | :---------: | :-------: | :---------: | :-------: | :-----------: | :-------: | :-------: |
| java<br>📈 | web<br>🌐 | framework<br>⚔ |jvm<br>⚙ | concurrent<br>⛓ | database<br>🗄 | algorithm<br>🔐 | os<br>📺 | network<br>📡 | interview<br>📧 |

## NOTES CATEGORY

#### 1.JAVA
- [Java 中的八种基本数据类型，以及它们的封装类]()
- [switch 能否用 String 做参数]()
- [equals 与 == 的区别]()
- [Object 有哪些共用的方法]()
- [Java 的四种引用，强弱软虚，用到的场景](xxx)
- [HashCode 的作用](http://www.baidu.com)
- [ArrayList、LinkedList、Vector 的区别]()
- [String、StringBuffer 与 StringBuilder 的区别]()
- [Map、Set、List、Queue、Stack 的特点与用法]()
- [HashMap 和 HashTable 的区别]()
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
- [实现多线程的两种方法：Thread 与 Runable]()
-  线程同步的方法：sychronized、lock、reentrantLock 等
-  锁的等级：方法锁、对象锁、类锁
-  写出生产者消费者模式
-  ThreadLocal 的设计理念与作用
-  ThreadPool 用法与优势
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

#### 2.JVM

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

#### 3.OS

- 进程和线程的区别
- 死锁的必要条件，怎么处理死锁
- Window 内存管理方式：段存储、页存储、段页存储
- 进程的几种状态
- IPC 几种通信方式
- 什么是虚拟内存
- 虚拟地址、逻辑地址、线性地址、物理地址的区别

------

#### 4.NETWORK

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

#### 5.ALGORITHM

- 链表与数组
- 队列和栈，出栈与入栈
- 链表的删除、插入、反向
- 字符串操作
- Hash 表的 hash 函数，冲突解决方法有哪些
- 各种排序：冒泡，选择，插入，希尔，归并，快排，堆排，桶排，基数的原理，平均时间复杂度，最坏时间复杂度、空间复杂度，是否稳定
- 快排的 partition 函数与归并的 Merge 函数
- 对冒泡与快排的改进
- 二分查找，与变种二分查找
- 二叉树，B+ 树， AVL 树，红黑树、哈弗曼树
- 二叉树的前中后序遍历：递归与非递归写法，层序遍历算法
- 图的 BFS 与 DFS 算法，最小生成树 prim 算法与最短路径 Dijkstra 算法
- KMP 算法
- 排列组合问题
- 动态规划、贪心算法、分治算法
- 大数据处理：类似 10 亿条数据找出最大的 1000 个数

------

#### 6.FRAMEWORK

- Hibernate
  - [hibernate 框架开发入门]()
  - [hibernate 事务以及丢失更新问题]()
  - [hibernate 级联删除以及级联更新问题]()
  - [hibernate 关于单表查询的几种方式]()
  - [hibernate 关于多表的查询]()

- Spring
- SpringMVC
- Shiro
- Dubbo
- Redis
- Mybatis

------

#### 7.FRONT-END

- Vue 篇
  - [Vue2 入门](11-前端技能/07-Vue.js/00-Vue2入门.md)
  - [Vue2 定义组件](11-前端技能/07-Vue.js/01-Vue2定义组件.md)
  - [Vue2 生命周期](11-前端技能/07-Vue.js/02-Vue2生命周期.md)
  - [Vue2 模板语法之插值](11-前端技能/07-Vue.js/03-Vue2模板语法之插值.md)
  - [Vue2 模板语法之指令](11-前端技能/07-Vue.js/04-Vue2模板语法之指令.md)
  - [Vue2 计算属性和侦听](11-前端技能/07-Vue.js/05-Vue2计算属性和侦听.md)
  - [Vue2 绑定 Class 和 Style](11-前端技能/07-Vue.js/06-Vue2绑定Class和Style.md)
  - [Vue2 条件渲染](11-前端技能/07-Vue.js/07-Vue2条件渲染.md)
  - [Vue2 列表渲染](11-前端技能/07-Vue.js/08-Vue2列表渲染.md)
  - [Vue2 事件处理](11-前端技能/07-Vue.js/09-Vue2事件处理.md)
  - Vue 使用路由
- NodeJs 篇
- Ajax异步请求
  
  - [Ajax异步请求](11-前端技能/04-Ajax异步请求/00-Ajax异步请求.md)
- Html 篇
- CSS3 篇
- JQuery 篇
  - [JQuery入门](11-前端技能/05-JQuery/00-JQuery入门.md)
  - [JQuery事件](11-前端技能/05-JQuery/01-JQuery事件.md)
  - [JQuery效果](11-前端技能/05-JQuery/02-JQuery效果.md)
  - [JQuery获取内容和属性](11-前端技能/05-JQuery/03-JQuery获取内容和属性.md)
  
  ## OTHER
