package com.lqwit.java.exception;

/**
 * 描述:
 *
 * @author liqiwen
 * @since 2018-05-01 11:51
 */
public class InheritingExceptions {

    public void f() throws SimpleException{
        System.out.println("Throw SimpleException from f()");
        throw new SimpleException();
    }

    public static void main(String[] args){
        InheritingExceptions ie = new InheritingExceptions();
        try {
            ie.f();
        }catch (SimpleException e){
            System.out.println("Caught it!");
        }
    }
}
