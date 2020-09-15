ThreadLocal 即线程变量，是一个以 ThreadLocal 对象为键、任意对象为值的存储结构。这个结构被附带在线程上，也就是说一个线程可以根据一个 ThreadLocal 对象查询绑定到这个线程上的值。ThreadLocal 为变量在每个线程中创建了一个副本，那么每个线程都可以访问自己内部的副本变量。

通过 set(T) 方法来设置一个值，在当前线程下通过 get() 方法获取到原先设置的值。

```java
ThreadLocal<String> stringThreadLocal = new ThreadLocal<>();
```



ThreadLocal 下面有如下几个方法需要注意

```java
public static <S> ThreadLocal<S> withInitial(Supplier<? extends S> supplier);
public T get();
public void set(T value);
public void remove();
```

withInitial 方法是一个初始化方法

```java
public static <S> ThreadLocal<S> withInitial(Supplier<? extends S> supplier) {
    return new SuppliedThreadLocal<>(supplier);
}
```



## Thread set 方法

```java
public void set(T value) {
  	//获取到当前线程
    Thread t = Thread.currentThread();
  	//获取到当前线程的 ThreadLocalMap
    ThreadLocalMap map = getMap(t);
    if (map != null)
        map.set(this, value);
    else
        createMap(t, value);
}
```

我们看到 set 方法做了如下几件事

1. 获取到当前线程
2. 获取到当前线程的 ThreadLocalMap
3. 如果 Map 存在，将值放入到 Map 中
4. 如果 Map 不存在，则创建一个 ThreadLocalMap

看看 ThreadLocalMap 这个类

```java
static class ThreadLocalMap {
  private Entry[] table; //存储值
  private static final int INITIAL_CAPACITY = 16;  //初始容量，必须为 2 的幂次方
  private int size = 0; //已存储的 size 大小
  private int threshold; //阈值
}
```

ThreadLocalMap 使用 Entry[] 数组来保存数据，Entry 是一个匿名内部类。

```java
static class Entry extends WeakReference<ThreadLocal<?>> {
  /** The value associated with this ThreadLocal. */
  Object value;

  Entry(ThreadLocal<?> k, Object v) {
    super(k);
    value = v;
  }
}
```
可以看到 Entry 继承了 WeakReference，value 就是我们要存入的值。

再看看 getMap 方法

```java
ThreadLocalMap getMap(Thread t) {
    return t.threadLocals;
}
```

返回了当前线程的 threadLocal， 实际上 threadLocals 就是当前线程的 ThreadLocalMap

最后看 createMap 方法

```java
void createMap(Thread t, T firstValue) {
    t.threadLocals = new ThreadLocalMap(this, firstValue);
}
```



## ThreadLocal get 方法

```java
public T get() {
  	//当前线程
    Thread t = Thread.currentThread();
  	//获取当前线程的 ThreadLocalMap
    ThreadLocalMap map = getMap(t);
    if (map != null) {
        ThreadLocalMap.Entry e = map.getEntry(this);
        if (e != null) {
            @SuppressWarnings("unchecked")
            T result = (T)e.value;
            return result;
        }
    }
    return setInitialValue();
}
```

获取当前线程，根据当前线程获取当前线程的 ThreadLocalMap。如果 map 不为空，从当前线程中获取 Entry 对象，如果当前 Entry 对象不为空，返回对应的值。

如果 map 或者 Entry 为空的话，就调用 setInitialValue 设置一个初始值。

```java
private T setInitialValue() {
    T value = initialValue();
    Thread t = Thread.currentThread();
  	//获取当前 ThreadLocalMap 对象
    ThreadLocalMap map = getMap(t);
    if (map != null)
      	//value == null，给当前线程的 ThreadLocalMap 设置成 null
        map.set(this, value);
    else
      	//创建一个 ThreadLocalMap，设置成 null
        createMap(t, value);
    return value;
}

protected T initialValue() {
   return null;
}
```

initialValue 就是赋值了一个 null 的值，然后获取当前线程，根据当前线程获取当前线程的 ThreadLocalMap 对象。如果 map 不为空，那么就设置值为 null，如果为空，就创建一个当前线程的 ThreadLocalMap 对象。



## ThreadLocal remove 方法

```java
public void remove() {
  	//获取当前线程的 ThreadLocalMap 对象
    ThreadLocalMap m = getMap(Thread.currentThread());
  	//不为空，直接移除就行
    if (m != null)
        m.remove(this);
}
```

很简单，获取当前线程的 ThreadLocalMap 对象。如果存在，直接移除就行。





