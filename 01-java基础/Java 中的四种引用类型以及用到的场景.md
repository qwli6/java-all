# Java 中的四种引用类型
java 对应的引用包括：强引用、软引用，弱引用以及虚引用。

从 jdk 1.2 开始，在 java.lang.ref 包下就提供了三个类：SoftReference（软引用），WeakReference（弱引用）以及 PhantomReference（虚引用），加上 StrongReference（强引用，文档中好像没有这种说法），总共是 4 种引用。



### 强引用
这个是最简单的，我们在编程的过程中几乎无时无刻都在使用强引用。举个最简单的例子：

```java
Object obj = new Object();
```

此时的 obj 就是一个强引用。任何被强引用指向的对象都不能被垃圾回收器回收，这些对象都是在程序中需要的，这个就不多说了。

### 软引用（SoftReference）
软引用需要通过 SoftReference 来实现，当一个对象只具有软引用时候，它有可能被垃圾回收器回收。对于只有软引用的对象而言，当系统内存空间足够时，他不会被系统回收，程序也可能使用该对象；当系统内存空间不足时候，系统将会回收塔。软引用通常用于对内存敏感的程序中。

jdk 文档对 SoftReference 的解释如下：

```java
Soft reference objects, which are cleared at the discretion of the garbage collector in response to memory demand. Soft references are most often used to implement memory-sensitive caches.
```

从上面的解释中我们可以看出，SoftReference 通常被使用在内存敏感的缓存区域中。




