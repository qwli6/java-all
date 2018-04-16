# Java 中的 IO

java 中提供了许多实现文件输入/输出的类，这些类可以分为：文本 I/O 类（text I/O class） 和 二进制 I/O 类（binary I/O class）。

# Java 中如何处理文本

java 中有许多用于各种目的的 I/O 类。通常，可以将它们分成输入类和输出类。输入类包含读取数据的方法，输出类包含写数据的方法。

例如 PrintWriter 就是一个输出类的例子，而 Scanner 是一个输入类的例子。

### 1. 在 java 中使用 Scanner 类读取文本数据，使用 PrintWriter 类写文本数据。

例如将文本写入一个名为 temp.txt 的文件中，可以使用 PrintWriter 类按如下方式创建一个对象：

```
PrintWriter output = new PrintWriter("temp.txt")
```

调用对象的 `print` 方法向文件中写入一个字符串。例如：

```
output.print("hello java");
```

调用 `close` 方法关闭文件

```
output.close();
```


### 2. 利用 Scanner 读取数据

```
 private static void readText(){
    //等待控制台输入
    //Scanner scanner =new Scanner(System.in);

    try {
        Scanner scanner =new Scanner(new File("temp.txt"));
        System.out.println(scanner.nextLine());
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
}
```

### 3. 总结
**输入对象从文件中读取数据流，输出对象将数据流写入文件，输入对象也成为输入流（input steam），输出对象也称为输出流（output stream）。**
