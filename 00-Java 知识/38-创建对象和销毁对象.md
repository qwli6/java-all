# 1. 考虑用静态方法代替构造器

我们知道，在 `java` 中，如果我们需要创建一个类的对象，就必须提供一个公有的构造器，然后通过 `new` 关键字去创建该对象的实例。比如：

```
//无参构造器
User user = new User();

//有参构造器
User user = new User("张三", 12);
```

这是最基本的创建对象的方法，当然，还有其他的创建对象的方法，例如

> 类可以提供一个公有的 `静态工厂方法` 来返回一个类的实例

例如下面这样：

```
public static Boolean valueOf(booean b){
	return b ? Boolean.TRUE : Boolean.FALSE
}
```

> 
> 注意： 这里的静态工厂方法并不等同于设计模式中的工厂模式

### 1. 提供静态工厂方法具有的几个优势

#### 1、静态工厂方法与构造器不同，它们有名称。

我们都知道，构造方法是没有返回值的，例如 `new BigInteger(int a, int b)`，这样返回的 `BigInteger` 对象可能是一个素数，但是如果使用 `BigInteger.probablePrime()` 这样的方法来提供 `BigInteger` 的对象，这样来返回对象的实例将会显得更加清楚。


#### 2、另外，静态工厂方法不必在每次调用他们的时候都创建一个新的对象的实例

虽然创建实例的开销不大，但是频繁的创建一个对象的实例，并不是一件好事。这个时候如果我们利用静态工厂方法来提供对象的实例，我们可以事先将要使用的对象实例缓存起来，进行重复利用。比如：

```
public class User{
	private static User user;
	
	private User(){
	
	}
	
	public static User createUser(){
		if(user == null){
			user = new User();
			return user;
		}
	
		return user;
	}
}

```

以上的写法和单例模式有点类似，但是这只能保证在单线程下 `User` 对象的实例可以被重复利用，如果是多线程的情况下，可能会出现多个 `User` 对象的问题。