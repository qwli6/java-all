## Coding-Interview-Notes

我是李启文，一个三流本科学校毕业的学生，于 2016 年 7 月 1 日正式毕业。2016 年 3 月 1 号就职于一家创业公司担任 Android 开发工程师至今，大学所学专业是**计算机科学与技术**，会写点后端，也会写点前端，最近在折腾着前后端分离的事情，不得不感慨：前端真的比后端难学！



前后端分离的作品：[点我试试](http://www.lqwit.com)，取名为**码农全家桶**，想知道是干什么的么？[点这里]()

- 关于 [码农全家桶](http://www.lqwit.com)，后端 API 代码在[这里](http://www.baidu.com)
- 关于 [码农全家桶](http://www.lqwit.com)，前端页面源码在[这里]()



同时我给 [码农全家桶](http://www.lqwit) 开放了 API 接口，如果你有兴趣，可以酌情使用一下我的接口。

- [测试接口]()
- [测试接口文档]()



> 各位大神：
>
> 由于网站是个人开发，服务器配置过低，还请各位大神高抬贵手，不要搞恶意破坏，感激不尽！如果有 Bug 发现，还请在 [这里]() 给我提出来，我一定尽快修复！



**「现已离职，正在全心全意面试找工作中…..」**

------



### 一、Java 基础篇

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


> 推荐书籍
> 
> 《Java 核心技术卷I》
> 
> 《Java 核心技术卷II》
> 
> 《Thinking in Java》
> 
> 《Java 并发编程实战》
> 
> 《Effective Java》
> 
> 《Head First 设计模式》
> 
> 《写给大忙人看的 Java SE 8》
> 

------



### 二、JVM 篇

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


> 推荐书籍
> 
> 《深入理解 Java 虚拟机》
> 



## 三、操作系统篇

- 进程和线程的区别
- 死锁的必要条件，怎么处理死锁
- Window 内存管理方式：段存储、页存储、段页存储
- 进程的几种状态
- IPC 几种通信方式
- 什么是虚拟内存
- 虚拟地址、逻辑地址、线性地址、物理地址的区别

> 推荐书籍
> 
> 《深入理解现代操作系统》
> 

------



### 四、网络篇

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

>推荐书籍
>
>《TCP/IP 协议族》
>
>《TCP/IP 详解 卷I：协议》

------



### 五、数据结构与算法篇

- 链表与数组
- 队列和栈，出栈与入栈
- 链表的删除、插入、反向
- 字符串操作
- Hash 表的 hash 函数，冲突解决方法有哪些
- 各种排序：冒泡，选择，插入，希尔，归并，快排，堆排，桶排，基数的原理，平均时间复杂度，最坏时间复杂度、空间复杂度，是否稳定
- 快排的 partition 函数与归并的 Merge 函数
- 对冒泡与快拍的改进
- 二分查找，与变种二分查找
- 二叉树，B+ 树， AVL 树，红黑树、哈弗曼树
- 二叉树的前中后序遍历：递归与非递归写法，层序遍历算法
- 图的 BFS 与 DFS 算法，最小生成树 prim 算法与最短路径 Dijkstra 算法
- KMP 算法
- 排列组合问题
- 动态规划、贪心算法、分治算法
- 大数据处理：类似 10 亿条数据找出最大的 1000 个数

> 刷题网站
> 
> 1. 牛客网
> 
> 2. leetcode
> 
> 3. 剑指 offer
>
> 
> 推荐书籍
> 
> 1.《大话数据结构》
> 
> 2.《剑指 offer》
> 
> 3.《编程之美》
> 

------



### 六、移动开发篇

- Activity 与 Fragment 的生命周期
- Activity 的四种启动模式与特点
- Activity 缓存方法
- Service 的声明周期，两种启动方法，有什么区别
- 怎么保证 Service 不被杀死
- 广播的两种注册方式，有什么区别
- Intent 的使用方法，可以传递那些数据类型。为什么 Intent 传递的数据类型对象都要实现 Parcelable
- ContentProvider 使用方法
- Thread、AsycTask、IntentService 的使用场景与特点
- 五种基本布局：FrameLayout，LinearLayout，AbsoluteLayout，RelativeLayout，TableLayout 各自的特点以及绘制效率对比
- Android 的数据存储形式
- Sqlite 的基本操作
- Android 中的 MVC 模式
- Merge、ViewStub 的作用
- Json 有什么优劣势
- 动画有哪几类？各有什么特点
- Handler、Loop 消息队列模型，各部分的作用
- 怎样退出终止 App
- Asset 目录与 res 目录的区别
- Android 怎么加速启动 Activity
- Android 的内存优化：ListView 优化，及时关闭资源，图片缓存等
- Android 中弱引用与软引用的应用场景
- Bitmap 的四种属性，与每种属性对应的大小
- View 与 ViewGroup 分类。自定义 View 过程：onMeasure()， onLayout()，onDraw()
- Touch 事件分发机制
- Android 长连接，怎么处理心跳机制
- Zygote 的启动过程
- Android IPC：Binder 原理
- 你用过什么框架，是否看过源码，是否知道底层原理
- Android 5.0 新特性
- Android 6.0 新特性
- Android 7.0 新特性
- Android 8.0 新特性

------



### 七、后端框架篇

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



### 八、前端开发篇

- Vue 篇
  - [Vue入门](07-Vue.js/00-Vue2入门.md)
  - [Vue定义组件](07-Vue.js/01-Vue2定义组件.md)
  - Vue 使用路由
- NodeJs 篇
- Html 篇
- CSS3 篇

------



### 九、版本控制与项目构建工具

- SVN
- Git
- Maven
- Gradle

------



### 十、项目相关


> 推荐书籍
> 
> 《疯狂 Android 讲义》
> 
> 《深入理解 Android》
> 
> 《Android 开发艺术探索》
> 
> 《Android 源码设计模式分析与实践》
> 

------



### 十一、面试相关问题总结



