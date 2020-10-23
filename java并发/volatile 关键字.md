Volatile 关键字

```java
private volatile int count;
```

主要有三个方面的作用

- 实现 long/double 类型变量的原子操作
- 防止指令重排序
- 实现变量的可见性

volatile double a = 1.0

当使用 volatile 修饰变量时，应用就不会从寄存器中获取该变量的值，而是从内存（高速缓存中）获取。



volatile 与锁类似的地方有两点：

- 确保变量的内存可见性
- 防止指令重排序



volatile 可以确保对变量写操作的原子性，但不具备排他性。

另外重要的一点在于：使用锁可能会导致线程的上下文切换（内核态与用户态之间的切换），但使用 volatile 并不会出现这种情况。

如果要实现 volatile 写操作的原子性，那么在等号右侧的赋值变量中就不能出现被多线程所共享的变量，哪怕这个变量是个 volatile 也不可以。

```java
volatile Date date = new Date();
```







```java
int a = 1;
String s = "hello world";

内存屏障（Release Barrier 释放屏障）
volatile boolean v = false; // 写入操作
内存屏障（Store Barrier 存储屏障）
```

Release Barrier:  防止下面的 volatile 与上面的所有操作进行指令重排序

Store Barrier: 重要作用是刷新处理器缓存，结果是可以确保该存储屏障之前一切的操作所生成的结果对于其他处理器来说都可见。



```java
内存屏障（Load Barrier, 加载屏障）
boolean v1 = v;
内存屏障（Acquire Barrier，获取屏障）

int a = 1;
String s = "hello";
```

Load Barrier: 可以刷新处理器缓存，同步其他处理器对该 volatile 变量的修改结果

Acquire Barrier：可以防止上面的 volatile 读取操作与下面的所有操作语句的指令重排序



对于 volatile 关键字变量的读写操作，本质上都是通过内存屏障来执行的。

内存屏障兼具了两方面的能力：1. 防止指令重排序  2. 实现变量内存的可见性。



对于读取操作来说：volatile 可以确保该操作与其后续的所有读写操作都不会进行指令重排序。

对于修改操作来说：volatile 可以确保该操作与其上面的所有读写操作都不会进行指令重排序。

























































