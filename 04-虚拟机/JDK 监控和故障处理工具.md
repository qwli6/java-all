在 jdk 1.8 中提供了很多工具，macos 可以在目录下看到 ` /Library/Java/JavaVirtualMachines/jdk1.8.0_111.jdk/Contents/Home/bin`

```shell
> /Library/Java/JavaVirtualMachines/jdk1.8.0_111.jdk/Contents/Home/bin ls -l
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 appletviewer
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 extcheck
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 idlj
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 jar
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 jarsigner
-rwxrwxr-x   1 root  wheel  103632 Sep 23  2016 java
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 javac
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 javadoc
-rwxrwxr-x   1 root  wheel    2293 Sep 23  2016 javafxpackager
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 javah
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 javap
-rwxrwxr-x   1 root  wheel    2293 Sep 23  2016 javapackager
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 jcmd
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 jconsole
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 jdb
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 jdeps
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 jhat
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 jinfo
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 jjs
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 jmap
-rwxrwxr-x   1 root  wheel     405 Sep 23  2016 jmc
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 jps
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 jrunscript
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 jsadebugd
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 jstack
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 jstat
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 jstatd
-rwxrwxr-x   1 root  wheel    5185 Sep 10  2014 jvisualvm
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 keytool
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 native2ascii
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 orbd
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 pack200
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 policytool
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 rmic
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 rmid
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 rmiregistry
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 schemagen
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 serialver
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 servertool
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 tnameserv
-rwxrwxr-x   1 root  wheel  116640 Sep 23  2016 unpack200
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 wsgen
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 wsimport
-rwxrwxr-x   1 root  wheel  103712 Sep 23  2016 xjc
```

对于这些工具，其实我们常用的就几种。

| 名称   | 主要作用                                                     |
| ------ | ------------------------------------------------------------ |
| jps    | JVM Process Status Tool，显示执行系统内所有的 HotSpot 虚拟机进程 |
| jstat  | JVM Statistics Monitoring Tool，用于手机 HotSpot 虚拟机各方面的运行时数据 |
| jinfo  | Configuration Info for Java，显示虚拟机配置信息              |
| jmap   | Memory Map for Java，生成虚拟机的内存转储快照                |
| jhat   | JVM Heap Dump Browser，用于分析 heapdump 文件，它会建立一个 HTTP/HTML 服务器，让用户可以在浏览器上查看分析结果 |
| jstack | Stack Trace for Java，显示虚拟机的线程快照                   |

关于 javac 和 java 命令就不做解释了。javac Test.java 就是编译 Java 文件，java Test.class 运行 Test.java 中的 main 方法。



## jps(jcmd)

```shell
jps [ options ] [ hostid ]
```

| 选项 | 作用                                                       |
| ---- | ---------------------------------------------------------- |
| -q   | 只输出 LVMID（进程 id），省略主类名称                      |
| -m   | 输出虚拟机进程启动时传递给主类 main 函数的参数             |
| -l   | 输出主类的全名，如果进程启动的是 jar 包，输出 jar 包的路径 |
| -v   | 输出虚拟机进程启动是的 JVM 参数                            |

**jps 原理：**

在 Java 程序启动后，会在 `tmp` 目录下生成一个名为 `hsperfdata_用户名 `的文件夹，在这个文件夹中会有一些以 java 进程 `pid `命名的文件。在我们使用 `jps `命令查询进程信息的时候，实际上就是将这个文件夹下的文件列出来，因此当这个文件夹为空或者这个文件夹的所有者和文件所属组权限与运行 Java 程序的用户权限不一致时，`jps`命令就查询不到该进程了。

执行 `jps` 查看进程时，需要和启动时的用户保持一致。

```shell
[root@wm-ly-02 hsperfdata_root]# jps -v
14416 Jps -Denv.class.path=.:/home/app/data/soft/jdk1.8.0_212/lib/dt.jar:/home/app/data/soft/jdk1.8.0_212/lib/tools.jar -Dapplication.home=/home/app/data/soft/jdk1.8.0_212 -Xms8m
21813 take-out-performance-prod-1.0.jar -Djob.switch=false -Xms1g -Xmx2g -XX:PermSize=256m -XX:MaxPermSize=256mi

[root@wm-ly-02 hsperfdata_root]# jps -l
21813 /home/app/data/jar/take-out-performance-prod-1.0.jar
```



## jstat

用于监视虚拟机各种运行状态信息的命令行工具。可以显示本地或者远程虚拟机进程中的类装载、内存、垃圾收集、JIT 编辑等运行数据。在没有 GUI 图形界面，只提供了纯文本控制台环境的服务器上，它将是运行期定位虚拟机性能问题的首选工具。

命令格式

**jstat [ option vmid [interval [s|ms] [ count ]] ]**

| 选项              | 作用                                                         |
| ----------------- | ------------------------------------------------------------ |
| -class            | 监视类装载、卸载数量、总空间以及类装载所耗费的时间           |
| -gc               | 监视 Java 堆状况，包括 Eden 区、两个 survivor 区、老年代、永久代等的容量、已用空间、GC 时间合计等信息 |
| -gccapacity       | 监视内容与 -gc 基本相同，但输出主要关注 Java 堆各个区域使用到的最大、最小空间 |
| -gcutil           | 监视内容与 -gc 基本相同，但输出主要关注已使用空间占总空间的百分比 |
| -gccause          | 与 -gcutil 功能一样，但是会额外输出导致上一次 GC 产生的原因  |
| -gcnew            | 监视新生代 GC 状况                                           |
| -gcnewcapacity    | 监视内容与 -gcnew 基本相同，输出主要关注使用到的最大、最小空间 |
| -gcold            | 监视老年代 GC 状况                                           |
| -gcoldcapacity    | 监视内容与 -gcold 基本相同，输出主要关注使用到的最大、最小空间 |
| -gcpermcapacity   | 输出永久代使用到的最大、最小空间                             |
| -compiler         | 输出 JIT 编译器编译过的方法、耗时信息                        |
| -printcompilation | 输出已经被 JIT 编译的方法                                    |
|                   |                                                              |
|                   |                                                              |

演示  `jstat -gcutil` 进程编号

```shell
[root@wm-ly-02 hsperfdata_root]# jstat -gcutil 21813
  S0     S1     E      O      M     CCS    YGC     YGCT    FGC    FGCT     GCT
 74.35   0.00  63.59  92.07  93.17  90.03  14004  159.016     4    0.502  159.518
[root@wm-ly-02 hsperfdata_root]#
```

- 新生代（Eden 区）使用了 63.59% 的空间。
- 两个 Survivor （s0 和 s1）分别占用了 74.35% 和 0.00% 的空间
- 老年代（Old）占用了 92.07% 的空间
- 程序运行以来共发生了 14004 次 Minor GC（YGC，Young GC），总耗时 159.016 秒，发生 Full GC（FGC 表示 Full GC） 4 次，总耗时 0.502 秒，所有 GC 总耗时 159.518 秒



## jinfo

jinfo 是查看 java 配置信息工具。作用是实时查看和调整虚拟机各项参数。





## jmap

jmap 是查看内存映像工具，用于生成堆转储快照（一般称为 heapdump 或者 dump 文件），通过 jvm 配置也可以让虚拟机在内存溢出异常时自动生成堆转储快照文件 `-XX:+HeapDumpOnOutOfMemoryError`。

用法：

```shell
> [root@wm-ly01 ~]# jmap -dump:format=b,file=oms.bin 6566
Dumping heap to /root/oms.bin ...
Heap dump file created
```

| 选项           | 作用                                                         |
| -------------- | ------------------------------------------------------------ |
| **-dump**      | 生成 java 堆转储快照。格式 -dump:[live,]format=b,file=[filename]，其中 live 参数说明只导出存活的对象 |
| -finalizerinfo | 显示在 F-Queue 中等待 Finalizer 线程执行 finalize 方法的对象。 |
| **-heap**      | 显示 Java 堆详细信息，如使用哪种回收器，参数配置，分代状况等。 |
| -histo         | 显示堆中对象统计信息，包括类、实例数量、合计容量等           |
| -permstat      | 以 ClassLoader 为统计口径显示永久代内存状态。                |
| -F             | 当虚拟机对 dump 指令没有响应时，可以使用 -F 参数强制生成 dump 快照。 |



## jhat

jhat 虚拟机堆转储快照分析工具。与 jmap 工具搭配使用。来分析 jmap 生成的堆转储快照。jmap 会开启一个 Http 服务，分析完毕后可供访问。

但是一般不建议使用 jhat 来进行分析，jhat 分析比较耗费机器资源。



## jstack

jstack 是用于生成虚拟机当前时刻的线程快照（一般称之为 threaddump 或者 javacore 文件）。线程快照就是当前虚拟机内每一条线程正在执行的方法堆栈集合。生成快照的目的通常是定位线程出现长时间停顿的原因，如线程间死锁、死循环、请求外部资源导致的长时间挂起等，都是导致线程长时间停顿的常见原因。

jstack 的命令格式

```
> jstack [ options ] vmid
```

options 的含义如下表

| 选项 | 作用                                                         |
| ---- | ------------------------------------------------------------ |
| -F   | to force a thread dump. Use when jstack <pid> does not respond (process is hung)、强制输出线程堆栈，当正常输出的请求不被响应时。 |
| -l   | long listing. Prints additional information about locks，除了堆栈外，显示关于锁的附加信息。 |
| -m   | to print both java and native frames (mixed mode)，如果调用到本地方法的话，可以显示 C/C++ 的堆栈 |
| -h   | 帮助信息                                                     |

使用示例

```shell
[root@wm-ly01 ~]# jstack -l 6566
```

从 jdk1.5 起，java.lang.Thread 类中提供了一个 getAllStackTraces() 方法用于获取虚拟机中所有线程的 StackTraceElement 对象。使用这个方法可以通过简单的几行代码完成 jstack 的大部分功能，在实际项目中可以调用这个方法做一个管理员页面，可以实时查看线程堆栈。

```java
public static void main(String[] args){
  	//获取所有线程的堆栈信息
    Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
    for(Thread thread: allStackTraces.keySet()){
        StackTraceElement[] stackTraceElements = allStackTraces.get(thread);
        if (thread.equals(Thread.currentThread())) {
            continue;
        }
        System.out.println("线程" + thread.getName() + "\n");

        for(StackTraceElement stackTraceElement: stackTraceElements){
            System.out.println("stackTraceElement:" + stackTraceElement);
        }
    }
}
```