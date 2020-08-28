ArrayList 是我们平时工作中最长用的一个集合，遇到什么东西，不管三七二十一，先 new ArrayList 再说。那么它的底层是如何设计和实现的，今天来探讨一番。



> ArrayList 是线程不安全的，基于数组实现。查询效率比较高，



首先，我们看看最常规的几种写法

```java
List<String> list = new ArrayList<>();

List<String> list1 = new ArrayList<>(10);
```

先看第一种的默认实现，点进去一窥究竟

```java
/**
 * Constructs an empty list with an initial capacity of ten.
 */
public ArrayList() {
    this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
}
```

DEFAULTCAPACITY_EMPTY_ELEMENTDATA 这个东西就是一个空的 object 对象，从描述上来说，初始的容量是 10，但是准确上来说，初始化并不是 10，如果不给定默认初始容量的话，并且不操作 add 方法的话，默认的容量就是 0

- 初始化容量为 10，只是在第一次 add 的时候，会去检查数组是否有位置可以存放元素，如果没有就进行扩容。

  