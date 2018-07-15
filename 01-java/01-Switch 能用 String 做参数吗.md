### Switch 条件判断中是否可以使用 String？

Java 中的条件判断有很多，if 语句是根据单独的一个 true 或者 false 条件作出选择的。如果过多的使用 if 将会让程序很难阅读，java 中提供 Switch 语句来有效的处理多重条件的问题。（ps： 实际上 Switch 处理多重条件的效率好像也不高。）


如果你是一个 Andorid 开发程序员，你应该经常会遇到这种代码：

```java
@OnClick({R.id.xxx, R.id.xxy})
public void onClickView(View view){
	switch(view.getId()){
		case R.id.xxx:
			// todo something
			break;
		case R.id.xxy:
			// todo something
			break;
		default:
			break;
		}
}
```

这就是一种典型的 Switch 处理多条件的方式，使用 Switch 表达式必须遵循以下规则：

- switch 表达式必须能计算出一个 char、byte、short、int 或者 String 型值，并且必须总是要用括号括住。

> 自 jdk 1.8 开始，switch 语句就已经支持 case 条件中使用 String 来作为条件了。如果你的 jdk 仍然是 1.7 及以前，switch 是不支持的。

- value1 ，...，valueN 必须与 switch 表达式的值具有相同的数据类型。注意： value1，...，valueN 都是常量表达式，也就是说这里的表达式是不能包含变量的。例如不允许出现 1+x。
- 当 Switch 表达式的值与 case 语句值相匹配的时候，执行从该 case 开始的语句，知道遇到一个 break 语句或者到达该 Switch 语句的结束。
- 默认情况（default）是可选的，当没有一个给出的 case 与 Switch 表达式相匹配的时候，default 就用来处理该条件。
- 关键字 break 是可选的。 break 会立即终止 Switch 语句。


> 注意：不要忘记在需要的时候添加 break 语句。一单匹配其中的一个 case， 就从匹配的 case 处开始执行，直到遇到 break 语句或者到达 switch 语句的结束。这种现象通常我们成为落空行（fall-through behavior）。例如下列代码为第 1 到第 5 天显示 weekdays，第 0 和第 6 天显示 weekends
> 

```java
switch(day){
	case 1:
	case 2:
	case 3:
	case 4:
	case 5:
		System.out.println("weekday");
		break;
	case 0:
	case 6:
		System.out.println("weekend);
		break;
	}

```

------

### 总结

> Switch 是可以使用 String 作为 case 语句中的常量值的，不过此时你的 jdk 版本必须要在 1.8 及以上。