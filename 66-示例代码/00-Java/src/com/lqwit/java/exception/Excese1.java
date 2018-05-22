package com.lqwit.java.exception;

/**
 * 描述:
 *
 * @author liqiwen
 * @since 2018-05-01 12:03
 */
public class Excese1 {


    public static void f() throws MyException{

        System.out.println("method f() has invoked!");
        throw new MyException("自定义异常，传递字符串作为参数");
    }

    public static void main(String[] args){

        try {
            f();
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }finally {
            System.out.println("这里是执行了 finally ");
        }

    }
}
