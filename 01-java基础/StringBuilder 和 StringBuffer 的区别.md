## StringBuffer 和 StringBuilder 有何区别？

先说结论:

> StringBuffer 是线程安全的，StringBuilder 是线程不安全的（在多线程的前提下）。



Q：为什么 StringBuilder 是线程不安全的？

一直都知道有这样的一个结论，但是从来没有去深究过为什么，现在打算彻底搞明白这个问题，防止下次被人问到捉襟见肘，语无伦次，词不达意。😥😥



### 一、问题重现

先看一段代码

```java
public class StringBuilderDemo {
    public static void main(String[] args) throws InterruptedException {

        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < 10; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int j = 0; j < 1000; j++) {
                        stringBuilder.append("a");
                    }
                }
            }).start();
        }

        Thread.sleep(2000);
        System.out.println("StringBuilder 的实际长度为: " + stringBuilder.length());
    }
}
```

这里我们使用了 10 个线程来向 StringBuilder 中追加值，每个线程中循环向 StringBuilder 中追加 1000 次，按照正常的逻辑来讲，最后执行完毕后，StringBuilder 中的字符串长度应该为 10 * 1000 = 10000 次。

但是实际输出的结果:

![image-20200817201713124](/Users/liqiwen/Library/Application Support/typora-user-images/image-20200817201713124.png)

这里输出的值并不唯一，多运行几次你会发现除了值不唯一外，当我们更改循环数量和次数的时候，这个异常的出现也不是必须的。既然值不准确，那么下面就来分析问题:



### 二、分析问题

**1、首先分析一下为什么值不准确**？

跟进 append 方法看看。

```java
@Override
public StringBuilder append(String str) {
    super.append(str);
    return this;
}
```

这里调用了父类的 append 方法，而 SpringBuilder 的父类就是 AbstractStringBuilder。在其父类中，可以看到父类中定义了两个成员变量

```java
/**
 * The value is used for character storage.
 */
char[] value;

/**
 * The count is the number of characters used.
 */
int count;
```

**char[] value 数组是用来存储字符串的值，int count 用来记录已经使用过的长度。**

这里的变量申明方式同 String 中的不一致，String 中对两个值均使用了 final 关键字来修饰。

下面接下来看父类中的 append 方法: 

```java
public AbstractStringBuilder append(String str) {
    if (str == null)
      return appendNull();
    //1. 获取字符串的长度
    int len = str.length();
    //2. 确保容量是否足够
    ensureCapacityInternal(count + len);
  	//3. 
    str.getChars(0, len, value, count);
  	//4. 已使用的长度 = 已使用的长度 + 字符串的长度
    count += len;
    return this;
}
```

这里可以先不看 1 2 3 步做了什么，我们只看第 4 步。第 4 步的操作不是原子性，如果当两个线程同时执行到这不一步，那么已使用的长度就会出现不准确的情况。

例如此时的 count 为 100，传进来的字符串长度为 1，当两个线程同时执行到第 4 步的时候，count 会被两个线程同时修改成 101，那么值不准确的原因我们就找到了，这也就是为什么我们实际输出的值要比 10 * 1000 小的原因。



**2、接下来分析为何可能会产生索引越界异常？**

我们看第 2 步，第 2 步实际上就是为了检查数组是否能装下新的字符串。如果不能装下，就应该调用 native 方法对数组进行扩容

```java
private void ensureCapacityInternal(int minimumCapacity) {
    // overflow-conscious code
    if (minimumCapacity - value.length > 0) {
      value = Arrays.copyOf(value,
                            newCapacity(minimumCapacity));
    }
}
// Arrays.copyof()
 public static char[] copyOf(char[] original, int newLength) {
   	 //重新 new 一个字符数组
     char[] copy = new char[newLength];
   	 //利用 native 方法进行拷贝
     System.arraycopy(original, 0, copy, 0,
                      Math.min(original.length, newLength));
     return copy;
 }
```

将已使用的长度 count + 要存入的字符串长度和值的长度作比较，显然两值相减大于 0，因此需要额外去申请存储空间。

```java
private int newCapacity(int minCapacity) {
    // overflow-conscious code
    int newCapacity = (value.length << 1) + 2;
    if (newCapacity - minCapacity < 0) {
      newCapacity = minCapacity;
    }
    return (newCapacity <= 0 || MAX_ARRAY_SIZE - newCapacity < 0) ? hugeCapacity(minCapacity) : newCapacity;
}

private int hugeCapacity(int minCapacity) {
    if (Integer.MAX_VALUE - minCapacity < 0) { // overflow
    		throw new OutOfMemoryError();
    }
    return (minCapacity > MAX_ARRAY_SIZE) ? minCapacity : MAX_ARRAY_SIZE;
}
```

申请存储空间的方式我们也可以看到，应该申请多长的长度作为新数组的大小呢？这里我们看到了一个移位` (value.length << 1) + 2;`操作，那么就可以说明：新数组的长度是原数组的长度 * 2 + 2 得出的。如果是第一次申请长度，那么新数组的长度就应该是 16 * 2 + 2 = 34。

为什么这里使用 16 来计算？

```java
public StringBuffer() {
    super(16);
}
```



第二个方法则是校验了是否存在内存溢出的操作。如果申请的长度超过了最大整型值，那么会抛出内存溢出错误。



如何将旧数组的值搬迁到新数组中去，实际上就是利用了 System.arrayCopy 来对数组进行拷贝。把旧的数组的值拷贝到新数组里面去，同时将新数组返回。



从堆栈的信息中我们可以看到，数组越界是通过 System.arrayCopy 中抛出来的，那么我们模拟一下拷贝流程，见 getChars 方法

```java
//srcBegin 从什么地方开始
//srcEnd 从什么地方结束
//dst[] 扩容后的数组
//dstBegin 拷贝后的位置
public void getChars(int srcBegin, int srcEnd, char dst[], int dstBegin) {
    if (srcBegin < 0) {
      throw new StringIndexOutOfBoundsException(srcBegin);
    }
    if (srcEnd > value.length) {
      throw new StringIndexOutOfBoundsException(srcEnd);
    }
    if (srcBegin > srcEnd) {
      throw new StringIndexOutOfBoundsException(srcEnd - srcBegin);
    }
    System.arraycopy(value, srcBegin, dst, dstBegin, srcEnd - srcBegin);
}
```

当两个线程交叉执行的时候，同时执行到 System.arraycopy 这个方法，刚好线程 1 在执行 System.arraycopy 时，cpu 分配给线程 1 的时间片刚好执行完毕，此时线程 2 执行拷贝并且执行完毕，此时 count+1，而这个时候线程 1 再去接着执行 System.arraycopy 就会抛出了 ArrayIndexOutOfBoundsException。



为什么又说只是可能会出现这种情况？

> 多线程的执行结果无法预料，无法预知到这个过程会发生几次。可能是 0 次，也可能会是 n 次，所以这里的结论只是说了可能会出现，但是并不代表一定会出现。



## StringBuffer 为何安全？

把上面的代码使用 StringBuffer 测试一遍，输出的结果如我们预期所料，返回了 10000。可以说明 StringBuffer 是线程安全的。那么为什么线程安全呢？直接进 append 方法看看。

```java
@Override
public synchronized StringBuffer append(String str) {
    toStringCache = null;
    super.append(str);
    return this;
}
```

方法中的 synchronized 关键字已经足够说明这一切了。