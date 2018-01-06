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

