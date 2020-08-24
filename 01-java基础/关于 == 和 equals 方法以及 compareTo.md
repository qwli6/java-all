## 字符串比较方法

常用的比较方法有很多，例如 == ，equals 以及 compareTo。

#### 1. compartTo

先来看一看文档的解释：

>**The comparison is based on the Unicode value of each character in the strings.**
>

大致意思就是 compareTo 方法是用来对两个字符串进行比较的，只不过它比较的依据不是通过比较引用值，也不是比较地址，而是根据字符串的 Unicode 编码来进行比较的。例如如下代码：

```java
class String01{
	public static void main(String[] args){
		String s1 = "abc";
		String s2 = "abf";
		System.out.println(s1.compareTo(s2));
	}
}
```

上面的程序输出的结果是 -3。如果我们将 s2 引用的值改为 “abc”， 那么 compartTo 方法将返回的值为 0。

> 如果 s1 和 s2 相等，那么该方法返回值为 0 ；如果按字典顺序（即以 Unicode 码的顺序）s1 < s2 ，那么该方法的返回值小于 0 ， 如果按字典顺序 s1 > s2 ，方法的返回值大于 0。
> 

接下来我们对上面的程序解释一波：

对于 s1 = “abc”， s2 = “abf”，s1.compareTo(s2) 返回的结果是 -3。首先比较的是 s1 中的第一个字符 a 与 s2 中的第一个字符 a，他们两个的第一个字符均相等，那么依次比较第二个字符（b 和 b），因为 第二个字符也相等，那么便比较第三个字符（c 和 f），显然 c 的 unicode 码比 f 的 unicode 码值要小 3 ，所以返回的数值为 -3。

> 注意：如果将 s1 和 s2 调换一个位置，那么将返回 3。

**compareTo 方法会根据一个字符串是否等于、大于或者小于另一个字符串，分别返回 0，正整数或者 负整数**

#### 2. 关于 == 操作符和 equals 方法

**2.1 ==  操作符默认比较的是对象的引用！也就是比较地址**

看下面的例子

```java
public class Equivalence{
	public static void main(String[] args){
		Integer i1 = new Integer(12);
		Integer i2 = new Integer(12);
		
		System.out.println(i1 == i2)
		System.out.println(i1 != i2)
	}
}

//Output
false
true
```

输出的结果是 false 和 true。这是为什么呢？ 因为 == 是比较的两个引用的地址，虽然两个引用指向的内容是一样的，但是他们的地址是不一样的，所以才会有这样的结果。


**2.2 equals 方法比较的是两个对象的内容是否相同，但不适用于基本类型**

如果我们要比较两个对象的实际内容是否相同，这个时候我们就需要使用所有对象都使用的特殊方法**equals()**。但是这个方法不适用于“基本类型”，基本类型直接使用 == 和 != 进行比较即可。

看下面的例子

```java
public class EqualsMethod{
	public static void main(String[] args){
		Integer i1 = new Integer(12);
		Integer i2 = new Integer(12);
		
		System.out.println(i1.equals(i2));
	}
}


//output
true
```

结果和我们预想的一样。

**2.3 默认的 equals 方法并不是比较的内容**

默认的 equals 方法并不是比较的内容，而是比较的是地址。

在看下面的例子

```java
class Value{
	int i;
}

public static void main(String[] args){
	Value v1 = new Value();
	Value v2 = new Value();
	v1.i = v2.i = 100;
	
	System.out.println(v1.equals(v2));
}

//output
false

```

返回的结果是 false，结果可能有点令人费解！我们知道，java 中所有的对象都是继承自 Object，**由于 Object 中的 equals 方法的默认行为是比较两个对象的引用**，所以如果我们不重写我们自定义的类中的 equals 方法而直接使用从 Object 方法中继承而来的 equals 方法，那么比较的结果自然是比较的引用而不是内容。


> 注意：大多数 java 中的类库都已经覆盖了默认的 equals 方法以便于用来比较对象的内容，而不是比较对象的引用。

