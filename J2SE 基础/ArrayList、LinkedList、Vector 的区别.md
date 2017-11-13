# ArrayList，LinkedList 以及 Vector 的区别

## ArrayList

ArrayList 底层采用数组来实现，默认初始化大小为 10，当存储空间不足够时候，会自动扩容。

```
/**
 * 默认初始化容量的声明
 * Default initial capacity.
 */
private static final int DEFAULT_CAPACITY = 10;

```

自动增容。每次增容大概是前一次的一半，也就是旧容量的 1.5 倍。

```
private void grow(int minCapacity) {
    // overflow-conscious code
    int oldCapacity = elementData.length;
    int newCapacity = oldCapacity + (oldCapacity >> 1);
    if (newCapacity - minCapacity < 0)
        newCapacity = minCapacity;
    if (newCapacity - MAX_ARRAY_SIZE > 0)
        newCapacity = hugeCapacity(minCapacity);
    // minCapacity is usually close to size, so this is a win:
    elementData = Arrays.copyOf(elementData, newCapacity);
}

```


特点：查询快，增删慢。


可以自己指定每次的初始化容量。

ArrayList实现原理：

> 1. 数组实现，查找快，增删慢。
> 
> 2. 数组为什么查询快？因为数组的内存空间地址是连续的。ArrayList 底层维护了一个 Object[] 用于存储对象，默认的长度是 10。可以通过 new ArrayList(20) 来显式的指定用于存储对象的数组的长度。当默认的或者指定的容量不够存储对象的时候，容量自动增长为原来的 1.5 倍。
> 
> 3. 由于 ArrayList 是通过数组来实现的，在增和删的时候会联系到数组增容问题，以及拷贝元素问题，所以慢。数组是直接按索引查找的，所以在查找的时候比较快。


验证：假设向数组的索引为 0 的地方添加元素，那么原来的索引为 0 位置的元素需要整体往后移动，如果空间不足的情况下，可能还需要扩容，一旦扩容，就需要将原数组的内容拷贝到新数组中，所以数组的增删效率是很低的。

具体我们可以看一下 add 方法中的源码：

```
public boolean add(E e) {
    ensureCapacityInternal(size + 1);  // Increments modCount!!
    elementData[size++] = e;
    return true;
}

// 发现 add 方法调用了 ensureCapacityInternal 方法，接着看这个方法
private void ensureCapacityInternal(int minCapacity) {
    if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
        minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
    }

    ensureExplicitCapacity(minCapacity);
}

// 同样 ensureCapacityInternal 调用了 ensureExplicitCapacity 方法，那么我们接着往下看
private void ensureExplicitCapacity(int minCapacity) {
    modCount++;

    // overflow-conscious code
    if (minCapacity - elementData.length > 0)
        grow(minCapacity);
}


// 这里调用了 grow 方法，就说明可能出现了存储空间不够用的情况，需要扩容。
private void grow(int minCapacity) {
    // overflow-conscious code
    int oldCapacity = elementData.length;
    int newCapacity = oldCapacity + (oldCapacity >> 1);
    if (newCapacity - minCapacity < 0)
        newCapacity = minCapacity;
    if (newCapacity - MAX_ARRAY_SIZE > 0)
        newCapacity = hugeCapacity(minCapacity);
    // minCapacity is usually close to size, so this is a win:
    // 这里就是拷贝数据元素
    elementData = Arrays.copyOf(elementData, newCapacity);
}

```

### 总结：
**ArrayList 增加删除元素效率不高，查找元素效率高，以为元素直接的地址空间是连续的。使我们日常开发中用的最多的一个集合**




## LinkedList

LinkedList 通过链表来实现，增删快，查找慢。

```

//通过链表实现，双向链表
private void linkFirst(E e) {
        final Node<E> f = first;
        final Node<E> newNode = new Node<>(null, e, f);
        first = newNode;
        if (f == null)
            last = newNode;
        else
            f.prev = newNode;
        size++;
        modCount++;
    }
```

由于 LinkedList 在内存中的地址不连续，需要让上一个元素记住下一个元素。所以每个元素中保存的有下一个元素的位置。虽然也有角标，但是查找的时候，需要从头往下找，显然是没有数组查找快的。但是，链表在插入新元素的时候，只需要让前一个元素记住新元素，让新元素记住下一个元素就可以了。所以插入很快。由于链表实现，增加时只要让前一个元素记住自己就可以，删除时让前一个元素记住后一个元素，后一个元素记住前一个元素。这样的增删效率较高。但查询时需要一个一个的遍历，所以效率较低。



> ArrayList 和 LinkedList 的存储查找的优缺点:
> > **1、ArrayList 是采用动态数组来存储元素的，它允许直接用下标号来直接查找对应的元素。但是，但是插入元素要涉及数组元素移动及内存的操作。总结：查找速度快，插入操作慢。**> 
> **2、LinkedList 是采用双向链表实现存储，按序号索引数据需要进行前向或后向遍历，但是插入数据时只需要记录本项的前后项即可，所以插入速度较快。**




## Vector 

Vector: 描述的是一个线程安全的 ArrayList。

> ArrayList： 单线程效率高
> Vector   ： 多线程安全的，所以效率低至于为什么说它是线程安全的 ArrayList？我们看源码就知道了。比如 add 方法的源码：

```
//Vector add() 源码
public synchronized boolean add(E e) {
    modCount++;
    ensureCapacityHelper(elementCount + 1);
    elementData[elementCount++] = e;
    return true;
}

// ArrayList add() 源码
public boolean add(E e) {
    ensureCapacityInternal(size + 1);  // Increments modCount!!
    elementData[size++] = e;
    return true;
}
```

最明显的区别就是 Vector 多了一个同步锁关键字： synchronized 。被 synchronized 修饰的部分称为临界区。所以 Vector 是线程安全的 ArrayList。