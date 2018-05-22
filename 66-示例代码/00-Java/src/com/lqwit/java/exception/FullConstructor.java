package com.lqwit.java.exception;

/**
 * 描述:
 *
 * @author liqiwen
 * @since 2018-05-01 11:57
 */
public class FullConstructor {

    public static void f() throws MyException{
        System.out.println("Throwing MyException from f()");
        throw new MyException();
    }

    public static void g() throws MyException{
        System.out.println("Throwing MyException from g()");
        throw new MyException("Originated in g()");
    }

    public static void main(String[] args){
        try{
            f();
        }catch (MyException e){
            e.printStackTrace(System.out);
        }

        try{
            g();
        }catch (MyException e){
            e.printStackTrace(System.out);
        }
    }
}
