# Object 中的公用方法

Object 是 java 中所有类的父类，一切不管是自定义的类还是 java 本身的类库中的类都是继承自 Object。该类是在 java.lang 包下。

以下是 jdk 文档中对于 Object 类的解释：

```java
public class Object

Class Object is the root of the class hierarchy. Every class has Object 
as a superclass. All objects, including arrays, implement the methods
of this class.
```

> 所有类的父类
>
> 所有类都实现了 Object 中的方法


Object 中的一些方法如下

- Object()

```java
//默认的构造方法，用于创建对象
Object() 
```

- clone()

```java
protected Object clone() throws CloneNotSupportedException
// Creates and returns a copy of this object.
// 创建并返回该对象的一个拷贝。
```

- equals()

```java
boolean equals(Object obj)
//Indicates whether some other object is "equal to" this one.
//比较两个对象的引用是否相等
```

- finalize()

```java
protect void finalize()
```

- getClass()

```java
public final Class<?> getClass()
```

- hashCode()

```java
public int hashCode()
```

- notify()

```java

```