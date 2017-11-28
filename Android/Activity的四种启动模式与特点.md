# Activity 的四种启动模式与特点

Activity 通常有四种启动模式，他们分别是 `standard`， `singleTop`，`singleTask`， `singleInstance`

## standard 启动模式

standard 启动模式是最简单也是最普通的一种启动模式，它最典型的特征就是类似于栈的功能：

**先进后出**

在 Activiy A 中定义按钮，设置监听，跳转到 Activity B 中，然后在 Activity B 中同样定义按钮，设置监听再跳转到 A，此时按下返回键，你会发现需要按三次返回键才能完全退出程序，执行顺序是 `A-B-A`，这就是典型的栈的特征，可以用下面的图来进行描述：






## singleTop 启动模式





## singleTask 启动模式


## singleInstance 启动模式