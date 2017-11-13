# try catch finally，try 中有 return， finally 还会走吗？

**try 中有 return， finally 中的代码依然会走。**

```

public class TryReturnRunTest {

    public static void main(String[] args){

        value();

        value2();
    }

    public static int value(){
        try{
            int i = 1/0;
            return 1;
        }catch (Exception e){

        }finally {
            //try 中 调用了 return ， finally 方法依然会被调用
            System.out.println("finally");
        }
        return 100;
    }

    public static int value2(){
        try{
            return 1;
        }finally {
            System.out.println("finally value2");
        }
    }

}


```

输出结果：

```

finally
finally value2

```

不管有没有异常，finally 中的代码一定会被执行。因此可以在 finally 方法中执行一些清理操作，比如关闭连接池，释放资源等。