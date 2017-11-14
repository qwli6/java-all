package com.lqwit.chapter12;

public class InheritException{

    public void fun() throws SimpleException{
        System.out.println("Throw SimpleException from fun()");
        throw new SimpleException();
    }


    public static void main(String[] args){
        InheritException inheritException = new InheritException();
        try {
            inheritException.fun();
        } catch (SimpleException e) {
            e.printStackTrace();
            System.out.println("caught it");
        }
    }
}

//定义自定义异常
class SimpleException extends Exception{}
