## Object 中的  wait 方法

wait 方法会导致当前线程等待直到其他线程调用此对象的 notify 或者 notifyAll 方法。**在调用 wait 方法之前，当前线程必须持有该对象的锁。调用 wait 方法之后，该对象会释放该对象的锁进入等待状态。**当其他线程调用了此对象的 notify 或者 notifyAll 后，此线程会重新获取对象的所有权并恢复执行。该方法与 wait(0) 类似。

> 通过以上的 java doc 解释，我们可以知道重要的三点： ==> 语义中描述的锁 == java doc 中的 monitor
>
> 1. 调用 wait 方法之前，当前线程必须已经获取到对象的锁了。
> 2. 调用 wait 方法之后，当前线程会释放当前对象的锁进行等待状态，直到其他线程调用到当前对象的 notify 或者 notifyAll 方法，等待在当前对象上的线程才会被唤醒。
> 3. 线程被唤醒后，也要重新去获取到当前对象的锁，获取到后才能恢复 wait 方法之后的逻辑继续执行。



在调用 wait 方法时，线程必须要持有被调用对象的锁，当调用了 wait 方法后，线程就会释放掉该对象的锁（monitor）

在调用 Thread 类的 sleep 方法时，线程是不会释放掉对象的锁的。



synchronized 关键字使用在代码块上

字节码层面会出现一个 monitorenter 和两个 monitorexit，表示锁的进入和退出（异常退出）

synchronized 关键字使用在方法上

字节码层面上会出现一个 flags：ACC_SYNCHRONIZED



## 关于 wait 与 notifyAll 和 notify 方法的总结

1. 当调用 wait 时，首先需要确保调用了 wait 方法的线程已经持有了对象的锁
2. 当调用了 wait 后，该线程就会释放掉这个对象的锁，然后进入到等待状态（wait set）放置到了一个 set 集合中
3. 当线程调用了 wait 后进入到等待状态时，他就可以等待其他线程调用相同对象的 notify 或者 notifyAll 方法来使得自己被唤醒
4. 一旦这个线程被其他线程唤醒后，该线程就会与其他线程一同开始竞争这个对象的锁（公平竞争）；只有当该线程获取到了这个对象的锁后，线程才会继续往下执行
5. 调用 wait 方法的代码片段需要放在一个 synchronized 块或是 synchronized 方法中，这样才可以确保线程在调用 wait 方法前已经获取到了对象的锁
6. 当调用对象的 notify 方法时，它会随机唤醒该对象的等待集合（wait set）中的任意一个线程，当某个线程被唤醒后，它就会与其他线程一同竞争对象的锁
7. 当调用对象的 notifyAll 方法时，它会唤醒该对象的等待集合（wait set） 中的所有线程，这些线程被唤醒后又会开始竞争对象的锁
8. 在某一时刻，只有唯一一个线程可以拥有对象的锁



## Thread 和 Sleep 和 await

Thread.sleep 与 await（或是 Object 的 wait 方法）的本质区别：sleep 方法本质上不会释放锁，**而 await 会释放锁，并且在 singal 后，还需要重新获得锁才能继续执行**（该行为与 Object 的 wait 方法完全一致）

## 问题

编写一个多线程程序，要求实现这样的一个目标：

- 存在一个对象，该对象有一个 int 类型的成员变量counter，该成员变量的初始值为 0.
- 创建两个线程，其中一个线程对该对象的成员变量 counter 增 1，另一个线程对该对象的成员变量减 1
- 输出该对象成员变量 counter 每次变化后的值
- 最终输出的结果应为：10101010101010101.... 交替输出



首先定义一个 MyObject 对象

```java
public class MyObject {

    private int counter;

    public synchronized void increase() {
        if(counter != 0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        counter ++;
        System.out.println(counter);
        notify();
    }

    public synchronized void decrease() {
        if(counter == 0){
            //不能再减少，需要等待
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        counter --;
        System.out.println(counter);
        notify();
    }
}
```

定义两个线程

```java
public class IncreaseThread extends Thread {
    private MyObject myObject;
    public IncreaseThread (MyObject myObject){
        this.myObject = myObject;
    }

  	@Override
    public void run() {
        for(int i = 0; i < 30; i++) {
            try {
                sleep((int) (Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myObject.increase();
        }
    }
}


public class DecreaseThread extends Thread {
    private MyObject myObject;
    public DecreaseThread (MyObject myObject){
        this.myObject = myObject;
    }

  	@Override
    public void run() {
        for(int i = 0; i < 30; i++) {
            try {
                sleep((int) (Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myObject.decrease();
        }
    }
}
```

运行 main 方法

```java
public class ClientDemo {
    public static void main(String[] args){
        MyObject object = new MyObject();
      
        IncreaseThread increaseThread = new IncreaseThread(object);
        DecreaseThread decreaseThread = new DecreaseThread(object);
      
        increaseThread.start();
        decreaseThread.start();
    }
}
```





































