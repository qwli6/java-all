## 面试题：“准备用HashMap存储 1w 条数据，构造方法传入 1w 进去会触发扩容吗？”

通过构造方法传入 1w，也就是 `Map<String, Object> map = new HashMap<String, Object>(10000)`，想要知道它是否会扩容，那就看这个构造方法做了啥？

```java
public HashMap(int initialCapacity) {
    this(initialCapacity, DEFAULT_LOAD_FACTOR);
}
```

调用了自身的另一个构造方法

```java
public HashMap(int initialCapacity, float loadFactor) {
    if (initialCapacity < 0)
        throw new IllegalArgumentException("Illegal initial capacity: " +
                                           initialCapacity);
    if (initialCapacity > MAXIMUM_CAPACITY)
        initialCapacity = MAXIMUM_CAPACITY;
    if (loadFactor <= 0 || Float.isNaN(loadFactor))
        throw new IllegalArgumentException("Illegal load factor: " +
                                           loadFactor);
    this.loadFactor = loadFactor;
    this.threshold = tableSizeFor(initialCapacity);
}
```

看到构造方法的最后 tableSizeFor 对传进来的容量进行了处理，看看处理方法

```java
static final int tableSizeFor(int cap) {
    int n = cap - 1;
    n |= n >>> 1;
    n |= n >>> 2;
    n |= n >>> 4;
    n |= n >>> 8;
    n |= n >>> 16;
    return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
}
```

调整成最接近 1 万的数值作为阈值，也就是 2<sup>14</sup> 次方 = 16384，也就是说我们传入的 1w 进来，最终会被调整成 16384 。

这个时候当我们调用 put 方法时，最后会调用 putVal 方法。

```java
final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
               boolean evict) {
    Node<K,V>[] tab; Node<K,V> p; int n, i;
    if ((tab = table) == null || (n = tab.length) == 0)
        n = (tab = resize()).length; //第一步
    if ((p = tab[i = (n - 1) & hash]) == null)
        tab[i] = newNode(hash, key, value, null);
    else {
        Node<K,V> e; K k;
        if (p.hash == hash &&
            ((k = p.key) == key || (key != null && key.equals(k))))
            e = p;
        else if (p instanceof TreeNode)
            e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
        else {
            for (int binCount = 0; ; ++binCount) {
                if ((e = p.next) == null) {
                    p.next = newNode(hash, key, value, null);
                    if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                        treeifyBin(tab, hash);
                    break;
                }
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    break;
                p = e;
            }
        }
        if (e != null) { // existing mapping for key
            V oldValue = e.value;
            if (!onlyIfAbsent || oldValue == null)
                e.value = value;
            afterNodeAccess(e);
            return oldValue;
        }
    }
    ++modCount;
    if (++size > threshold)
        resize(); //第五步
    afterNodeInsertion(evict);
    return null;
}
```

最开始进入时 table 为空，触发初始化操作

```java
final Node<K,V>[] resize() {
    Node<K,V>[] oldTab = table;
    int oldCap = (oldTab == null) ? 0 : oldTab.length; //第一步
    int oldThr = threshold;
    int newCap, newThr = 0;
    if (oldCap > 0) {
        if (oldCap >= MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return oldTab;
        }
        else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                 oldCap >= DEFAULT_INITIAL_CAPACITY)
            newThr = oldThr << 1; // double threshold
    }
    else if (oldThr > 0) // initial capacity was placed in threshold
        newCap = oldThr; //第二步
    else {               // zero initial threshold signifies using defaults
        newCap = DEFAULT_INITIAL_CAPACITY;
        newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
    }
    if (newThr == 0) { //第三步
        float ft = (float)newCap * loadFactor;
        newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                  (int)ft : Integer.MAX_VALUE);
    }
    threshold = newThr; //第四步
    @SuppressWarnings({"rawtypes","unchecked"})
        Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
    table = newTab;
    if (oldTab != null) {
        for (int j = 0; j < oldCap; ++j) {
            Node<K,V> e;
            if ((e = oldTab[j]) != null) {
                oldTab[j] = null;
                if (e.next == null)
                    newTab[e.hash & (newCap - 1)] = e;
                else if (e instanceof TreeNode)
                    ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                else { // preserve order
                    Node<K,V> loHead = null, loTail = null;
                    Node<K,V> hiHead = null, hiTail = null;
                    Node<K,V> next;
                    do {
                        next = e.next;
                        if ((e.hash & oldCap) == 0) {
                            if (loTail == null)
                                loHead = e;
                            else
                                loTail.next = e;
                            loTail = e;
                        }
                        else {
                            if (hiTail == null)
                                hiHead = e;
                            else
                                hiTail.next = e;
                            hiTail = e;
                        }
                    } while ((e = next) != null);
                    if (loTail != null) {
                        loTail.next = null;
                        newTab[j] = loHead;
                    }
                    if (hiTail != null) {
                        hiTail.next = null;
                        newTab[j + oldCap] = hiHead;
                    }
                }
            }
        }
    }
    return newTab;
}
```

第一步：table 为空，那么此时旧的 oldCap 即为 0，oldThr 为我们 HashMap 调整过的数值 16384，这是调用之前的一组旧数据。

第二步：发现 oldThr 大于 0，于是将 oldThr 赋值给新容量 newCap

第三步：重新计算新的 newThr 阈值，此时的阈值就等于新容量*负载因子 = 16483 * 0.75 = 12288

第四步：把计算出来的阈值赋值给旧的阈值，此时就得出了新的容量为 16384，新的阈值为 12888

第五步：看 putVal    `if (++size > threshold)  resize(); ` 最后面的的判断，触发扩容的条件。当放置到 1w 条数据的时候，并没有超过 12888 这个阈值，所以不会触发扩容。





如果我们将初始化大小调整至 1000，会触发扩容吗？

还是按照上面的思路来，如果我们传入 1000，那么 HashMap 会帮我们动态将存储大小调整为 1024。此时会重新计算出新的阈值和新的容量

新容量还是 1024，但是新的阈值就变成了 1024 * 0.75 = 768，此时的 HashMap 是不足以承载 1k 条数据的，所以它在被添加到 1k 条数据的时候还是会动态触发一次扩容。



- 扩容是比较耗费资源的操作，如果能在初始化之前确定有多少元素，可以直接指定数量，避免 HashMap 的动态扩容
- 具体如何触发扩容是依赖容量 * 负载因子来决定的
- 我们传入的容量，HashMap 不会使用，但是会去取最接近的一个数来作为容量（必须是 2 的幂次方），用这个数乘以默认的负载因子才是阈值，到达这个阈值才发主动触发扩容