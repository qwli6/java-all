package com.lqwit.java.exception;

/**
 * 描述:
 *  定义一个引用，初始化为空，用该引用调用方法，用 try catch 捕获异常
 * @author liqiwen
 * @since 2018-05-01 12:03
 */
public class Excese2 {


    public void f() {
        System.out.println("method f() has invoked!");
    }

    public static void main(String[] args){
        Excese2 excese2 = null;
        try {
            excese2.f();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Caught it");
            System.out.println(e.getMessage());
        }

    }
}
