## volatile 和 synchronized 关键字

Java 支持多个线程同时访问一个对象或者对象的成员变量，由于每个线程中可以拥有这个对象的拷贝。



关键字 volatile 可以用来修饰字段（成员变量），就是告知程序任何对该变量的访问均需要从共享内存中获取，而且对它的改变必须同步刷新回共享内存，它能保证所有线程对变量访问的可见性。



关键字 synchronized 可以修饰方法或者以同步块的形式来进行使用，它主要确保多个线程在同一时刻，只有一个线程处于方法或者同步块中，保证了线程对变量访问的可见性和排他性。



```java
public class SynchronizedTest {

    public static void main(String[] args){

        //对 SynchronizedTest.class 对象加锁
      	//同步块
        synchronized (SynchronizedTest.class){

        }

        //对方法加锁
        m();
    }
		
  	//同步方法
    public synchronized static void m() {

    }
}
```

通过使用 **javap -v SynchronizedTest.class** 对象，可以看到 SynchronizedTest 的字节码文件。

```java
 public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V //入参描述 String 类型
    flags: ACC_PUBLIC, ACC_STATIC  //公共的 静态的
    Code:
      stack=2, locals=3, args_size=1
         0: ldc           #2                  // class me/lqw/interview/thread/SynchronizedTest
         2: dup
         3: astore_1
         4: monitorenter  //监视器进入
         5: aload_1  //执行同步块
         6: monitorexit //监视器退出
         7: goto          15
        10: astore_2
        11: aload_1
        12: monitorexit
        13: aload_2
        14: athrow
        15: invokestatic  #3                  // Method m:()V  调用静态方法
        18: return
                  
     //..... 省略了部分输出             
```



```java
 public static synchronized void m();
    descriptor: ()V  //无参数描述
    flags: ACC_PUBLIC, ACC_STATIC, ACC_SYNCHRONIZED //公共的，静态的，同步的
    Code:
      stack=0, locals=0, args_size=0
         0: return
      LineNumberTable:
        line 18: 0

```

综上: 可以看到 同步块使用监视器 `monitorenter、monitorexit `来保证同步，监视器是一个**排他**的动作，也就是说同一时间只能有一个线程能获取到监视器。同步方法在方法上加了一个 `ACC_SYNCHRONIZED` 标志位来表示该方法是同步方法（本质也是监视器）。执行同步方法或者同步代码块的线程必须要先获取到方法或者对象的监视器，才能执行。而没有获取到方法或者监视器的线程将会被阻塞在同步块和同步方法的入口处，即 BLOCKED 状态。



