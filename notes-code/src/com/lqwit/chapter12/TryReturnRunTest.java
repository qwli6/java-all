package com.lqwit.chapter12;

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
