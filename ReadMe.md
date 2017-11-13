# 写在前面

楼主是一个某三流学校的学生，于 2016 年 7 月 1 日正式毕业。2016 年 3 月 1 号就职于一家创业公司担任 Android 开发工程师至今。虽然在一家小公司混吃等死也算安逸，但是越工作越心惊胆颤，越发认识自己身上的不足以及技术方面的欠缺，更重要的是不满足于现在的工资了，于是有了跳槽的打算。

既然是跳槽，必定需要储备一些知识技术，以上是我罗列的一些技术知识点，大部分问题都来自于互联网！打算从头开始，一点一点积累。这里引用一句网上的话：

> 1、没有太多的项目经验不是那么重要，当你的基础好到令人发指的时候，其实也是可以进入大公司“划水“的。
> 
> 2、大公司都是比较看中数据结构与算法的，So：多锻炼算法以及手写代码的能力，好记性不如烂笔头这句话不是没有道理的。
> 

大纲目录如下：

# 一、Java 基础

### <a href="Java/1.Java 的八种数据类型.md">1. Java 中的八种基本数据类型，以及它们的封装类</a>

### <a href="Java/2. switch 能用 String 做参数吗.md">2. switch 能否用 String 做参数？</a>

### <a href="Java/3.关于 == 和 equals 方法以及 compareTo.md">3. equals 与 == 的区别？</a>

### <a href="Java/4.Object 中的共用方法.md">4. Object 有哪些共用的方法？</a>

### <a href="Java/5.Java 中的四种引用类型以及用到的场景.md">5. Java 的四种引用，强弱软虚，用到的场景。</a>

### 6. HashCode 的作用

### <a href="Java/ArrayList、LinkedList、Vector 的区别.md">7. ArrayList、LinkedList、Vector 的区别</a>

### <a href="Java/8.String，StringBuilder 以及 StringBuffer 的区别.md">8. String、StringBuffer 与 StringBuilder 的区别。</a>

### 9. Map、Set、List、Queue、Stack 的特点与用法。

### 10. HashMap 和 HashTable 的区别。

### 11. HashMap 和 ConcurrentHashMap 的区别，HashMap 的底层源码。

### 12. TreeMap、HashMap、LinkedHashMap 的区别。

### 13. Collection 包结构，与 Collections 的区别。

### <a href="Java/try catch finally, try 中有 return，finally 还走吗？.md">14. try catch finally， try 里面有 return， finally 还执行吗？</a>

### 15. Exception 与 Error 包结构。OOM 你遇到过哪些情况， SOF 你遇到过哪些情况。

### 16. Java 面向对象的三个特征与含义。

### 17. Override 和 Overload 的含义与区别。

### 18. interface 与 abstract 的区别。

### 19. static class 和 non static class 的区别。

### 20. Java 多态的实现原理

### 21. 实现多线程的两种方法：Thread 与 Runable

### 22. 线程同步的方法：sychronized、lock、reentrantLock 等

### 23. 锁的等级：方法锁、对象锁、类锁

### 24. 写出生产者消费者模式

### 25. ThreadLocal 的设计理念与作用

### 26. ThreadPool 用法与优势

### 27. Concurrent 包中的其他东西： ArrayBlockingQueue，CountDownLatch 等

### 28. wait() 和 sleep() 的区别

### 29. foreach 与正常 for 循环效率对比

### 30. Java IO 与 NIO

### 31. 反射的作用与原理

### 32. 泛型常用特点，List《String》 能否转换成 List《Object》

### 33. 解析 XML 的几种方式的原理与特点：DOM、SAX、PULL

### 34. Java 与 C++ 对比

### 35. Java1.7 与 1.8 新特性

### 36. 常用的设计模式：单例、工厂、适配器、责任链、观察者等

### 37. JNI（Java Native Interface） 的使用


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

# 二、深入 Java 虚拟机（JVM - Java Virtual Machine）

### 1. 内存模式以及分区，需要详细到每个区放什么

### 2. 堆里面的分区：Eden、survival from to、老年代各自的特点

### 3. 对象的创建方法、对象的内存分配、对象的访问定位

### 4. GC 的两种判定方法：引用计数与引用链

### 5. GC 的三种手机方法：标记清除、标记整理、复制算法的原理与特点，分别用在什么地方，如果让你优化收集方法，有什么思路？

### 6. GC 收集器有哪些？ CMS 收集器与 G1 收集器的特点。

### 7. Minor GC 与 Full GC 分别在什么时候发生？

### 8. 几种常用的内存调试工具：jmap、jstack、jconsole

### 9. 类加载的五个过程：加载，验证，准备，解析，初始化

### 10. 双亲委派模型：Bootstrap ClassLoader、Extension ClassLoader、ApplicationClassLoader

### 11. 分配：静态分派与动态分派


> 推荐书籍
> 
> 《深入理解 Java 虚拟机》
> 

# 三、开发不可少了它-操作系统（Operating System）

### 1. 进程和线程的区别

### 2. 死锁的必要条件，怎么处理死锁

### 3. Window 内存管理方式：段存储、页存储、段页存储

### 4. 进程的几种状态

### 5. IPC 几种通信方式

### 6. 什么是虚拟内存

### 7. 虚拟地址、逻辑地址、线性地址、物理地址的区别

> 推荐书籍
> 
> 《深入理解现代操作系统》
> 

# 四、TCP/IP 协议

### 1. OSI 与 TCP/IP 各层的结构与功能，都有那些协议

### 2. TCP 与 UDP 的区别

### 3. TCP 报文结构

### 4. TCP 的三次握手与四次挥手过程，各个状态名称与含义， TIMEWAIT 的作用。

### 5. TCP 拥塞控制

### 6. TCP 滑动窗口与回退 N 帧协议

### 7. Http 的报文结构

### 8. Http 的状态码含义

### 9. Http Request 的几种类型

### 10. Http1.1 和 Http1.0 的区别

### 11. Http 怎么处理长连接

### 12. Cookie 与 Session 的作用与原理

### 13. 电脑上访问一个网页，整个过程是怎样的：DNS、HTTP、TCP、OSPF、IP、ARP

### 14. Ping 的整个过程。ICMP 报文是什么。

### 15. C/S 模式下使用 socket 通信，几个关键函数

### 16. IP 地址分类

### 17. 路由器与交换机区别

>推荐书籍
>
>《TCP/IP 协议族》
>
>《TCP/IP 详解 卷I：协议》
>

# 五、数据结构与算法

### 1. 链表与数组

### 2. 队列和栈，出栈与入栈

### 3. 链表的删除、插入、反向

### 4. 字符串操作

### 5. Hash 表的 hash 函数，冲突解决方法有哪些？

### 6. 各种排序：冒泡，选择，插入，希尔，归并，快排，堆排，桶排，基数的原理，平均时间复杂度，最坏时间复杂度、空间复杂度，是否稳定

### 7. 快排的 partition 函数与归并的 Merge 函数

### 8. 对冒泡与快拍的改进

### 9. 二分查找，与变种二分查找

### 10. 二叉树，B+ 树， AVL 树，红黑树、哈弗曼树

### 11. 二叉树的前中后序遍历：递归与非递归写法，层序遍历算法

### 12. 图的 BFS 与 DFS 算法，最小生成树 prim 算法与最短路径 Dijkstra 算法

### 13. KMP 算法

### 14. 排列组合问题

### 15. 动态规划、贪心算法、分治算法

### 16. 大数据处理：类似 10 亿条数据找出最大的 1000 个数

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

# 六、Android 基础知识

### 1. Activity 与 Fragment 的生命周期

### 2. Activity 的四种启动模式与特点

### 3. Activity 缓存方法

### 4. Service 的声明周期，两种启动方法，有什么区别

### 5. 怎么保证 Service 不被杀死

### 6. 广播的两种注册方式，有什么区别

### 7. Intent 的使用方法，可以传递那些数据类型。为什么 Intent 传递的数据类型对象都要实现 Parcelable 

### 8. ContentProvider 使用方法

### 9. Thread、AsycTask、IntentService 的使用场景与特点

### 10. 五种基本布局：FrameLayout，LinearLayout，AbsoluteLayout，RelativeLayout，TableLayout 各自的特点以及绘制效率对比

### 11. Android 的数据存储形式

### 12. Sqlite 的基本操作

### 13. Android 中的 MVC 模式

### 14. Merge、ViewStub 的作用

### 15. Json 有什么优劣势

### 16. 动画有哪几类？各有什么特点？

### 17. Handler、Loop 消息队列模型，各部分的作用

### 18. 怎样退出终止 App

### 19. Asset 目录与 res 目录的区别

### 20. Android 怎么加速启动 Activity

### 21. Android 的内存优化：ListView 优化，及时关闭资源，图片缓存等

### 22. Android 中弱引用与软引用的应用场景

### 23. Bitmap 的四种属性，与每种属性对应的大小

### 24. View 与 ViewGroup 分类。自定义 View 过程：onMeasure()， onLayout()，onDraw()

### 25. Touch 事件分发机制

### 26. Android 长连接，怎么处理心跳机制

### 27. Zygote 的启动过程

### 28. Android IPC：Binder 原理

### 29. 你用过什么框架，是否看过源码，是否知道底层原理

### 30. Android 5.0 新特性

### 31. Android 6.0 新特性

### 32. Android 7.0 新特性

### 33. Android 8.0 新特性


# 七、版本控制与项目构建工具

### 1. SVN 

### 2. Git


### 3. Maven

### 4. Gradle


# 八、项目相关


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

# 九、面试相关问题总结



