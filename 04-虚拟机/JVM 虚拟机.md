## Java 虚拟机的生命周期

在如下几种情况下，Java 虚拟机将结束自己的生命周期

- 程序正常结束
- 执行了 System.exit() 方法
- 程序在执行过程中遇到了异常或者错误而异常终止
- 由于操作系统中出现了错误而导致 Java 虚拟机进程终止



## 类的加载

有两种类型的类加载器

- Java 虚拟机自带的加载器
  - 根类加载器（启动类加载器）Boostrap
  - 扩展类加载器（Extension）
  - 系统（应用）类加载器（System）
- 用户自定义的类加载器
  - Java.lang.ClassLoader的子类
  - 用户可以定制类的加载方式

## 类的初始化时机

当 Java 虚拟机初始化一个类时，要求它的所有的父类都已经被初始化，但是这条规则并不适用于接口。

- 在初始化一个类时，并不会先初始化它所实现的接口。
- 在初始化一个接口时，并不会先初始化它的父接口。

因此一个父接口并不会因为它的子接口后者实现类的初始化而初始化。只有当程序首次使用特定接口的静态变量时，才会导致该接口的初始化。



## 获取类加载器

- 获取当前类的 ClassLoader
  - clazz.getClassLoader();
- 获取当前线程上下文的 ClassLoader
  - Thread.currentThread().getContextClassLoader();
- 获得系统的 ClassLoader
  - ClassLoader.getClassLoader();
- 获取调用者的类加载器
  - DriverManager.getCallerClassLoader();

```java
-XX:+TraceClassLoading   #打印出所有的加载类
-XX:+TraceClassunLoading  #打印出所有被卸载的类  
```



## 类的加载过程

加载：就是把二进制的 java 类型读入到内存中

连接：

- 验证：确保被加载的类的正确性
- 准备：为类变量分配内存，设置**默认值**。但是在达到初始化之前，类变量都没有被初始化成真正的值
- 解析：解析过程就是在类型的常量池中寻找类、接口、字段和方法的符号引用，把这些符号引用替换成直接应用的过程

初始化：为类的变量赋予真正正确的值。

类实例化：

- 为新的对象分配内存
- 为实例变量赋予默认值
- 为实例变量赋予正确的初始值
- Java 编译器为它编译的每一个类都至少生成了一个实例化方法，在 Java 的 class 文件中，这个实例化方法初始化被称之为 init。针对源代码中的构造方法，会为每一个类都生成一个 init 方法。











## ClassLoader Java Doc

```tex
Class objects for array classes are not created by class loaders, but are created automatically as required by the Java runtime. The class loader for an array class, as returned by Class.getClassLoader() is the same as the class loader for its element type; if the element type is a primitive type, then the array class has no class loader.
```

对于数组对象到的 class 文件并不是由类加载器来创建的，它是在 Java 虚拟机在运行时间根据需要自动创建的。对于数组的类加载器来说，通常调用 getClassLoader 会返回数组里面的元素类型的类加载器是一致的。如果数组的原生数据类型为原生类型，那么这个数组没有是没有 classLoader。

```java
public class Test7 {
    public static void main(String[] args){
        String[] strings = new String[2];
        System.out.println(strings.getClass());
        System.out.println(strings.getClass().getClassLoader()); //String 的类加载器，也就是启动类加载器，null
        System.out.println("-------------");
        Test7[] myTests = new Test7[2];
        System.out.println(myTests.getClass().getClassLoader()); //应用类加载器 AppClassLoader
        System.out.println("-------------");
        int[] ids = new int[2];
        System.out.println(ids.getClass().getClassLoader());  //没有类加载器 ,null
    }
}

// console
class [Ljava.lang.String;  //引用类型 L
null     //这里说明使用了启动类加载器
-----------
sun.misc.Launcher$AppClassLoader@18b4aac2
-------------
null    // 这里说明没有类加载器
```





## 类加载器的双亲委托模型好处

- 可以确保 Java 核心库的类型安全：所有的 Java应用都至少会引用 java.lang.Object 这个类，也就是说在运行期，java.lang.Object 这个类会被加载到 Java 虚拟机中；如果这个加载过程中是由 Java 应用自己的类加载器所完成的，那么很可能就会在 JVM 存在多个版本的 java.lang.Object 类，而且这些类之间还是不兼容的，相互不可见的（正是命名空间在发挥着作用）。借助于双亲委托机制，Java 核心类库中的类的加载工作都是由启动类加载器来统一完成，从而确保了 Java 应用所使用的都是同一个版本的 Java 核心类库，它们之间是相互兼容的。
- 可以确保 Java 核心类库所提供的类不会被自定义的类所替代。
- 不同的类加载器可以为相同名称（binary name）的类创建额外的命名空间。相同名称的类可以并存在 Java 虚拟机中，只需要用不同的类加载器来加载他们即可。不同类加载器所加载的类之间是不兼容的，这就相当于在 Java 虚拟机中内部创建了一个又一个相互隔离的 Java 类空间，这类技术在很多框架中都得到了实际应用。











CPU 100% 排查方式

- top -c 显示运行中的进程列表信息
- 根据进程号找到 cpu 占有率最高的线程号  top -Hp {pid}，这里是十进制需要转换成十六进制
- 利用 jstack 生成虚拟机中所有的线程快照， jstack -l {pid} > text.txt
- 对线程快照进行分析，可通过 grep 查找十六进制的文件，看看线程号在干啥

内存泄露问题排查

- 一样是通过 top -c 显示正在运行中的进程列表信息（或者使用 jps -l 也可以）
- 通过 jmap 生成堆转储快照， jmap -dump:file=text,format=b {pid}
- 利用 jvivm 分析，看看内存使用情况，如果没有办法导入到本地，可以通过 jhat 分析









































