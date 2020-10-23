ThreadLocal

本质上，ThreadLocal 是通过空间来换取时间的，从而实现每个线程中都会有一个变量的副本，这样每个线程就都会操作该副本，从而完全规避了多线程的并发问题。



对于  ThreadLocal 对象，我们会使用如下的方式来使用

```java
public class Test {
	private static final ThreadLocal<String> tl = new ThreadLocal();
}
```

如果我们不需要 ThreadLocal 对象了，可以使用如下的方式

```java
try{
  // ...
  // ...
 
} finally {
  tl.remove();
}
```













Java 中存在四种类型的引用

- 强引用（strong）
- 软引用（soft）
  - 当内存空间不够的时候，回收软引用指向的内存空间
- 弱引用（weak）
  - 当内存空间不够的时候，GC 会在下一次垃圾回收的时候回收内存空间，前提是没有强引用的类型指向该弱引用所指向的内存空间
- 虚引用（phantom）