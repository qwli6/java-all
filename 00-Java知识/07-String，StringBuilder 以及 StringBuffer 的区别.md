# String， StringBuffer 以及 StringBuilder 的区别。

### 1. String

String 类是一个不可变类，即一旦一个 String 对象被创建，它包含在这个对象中的字符串序列是不可改变的，直至该对象被销毁。

String 类对象是 final 类，所有被 final 修饰的对象是不可被改变的，从声明到第一次初始化的过程之后，便不可以再次被修改。