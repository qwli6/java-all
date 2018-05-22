package com.lqwit.java.exception;

/**
 * 描述:
 *  数组越界异常
 * @author liqiwen
 * @since 2018-05-01 12:03
 */
public class Excese3 {

    private String[] str = {"hello", "world"};

    public static void main(String[] args){
        Excese3 excese3 = new Excese3();
        try {
            System.out.println(excese3.str[3]);
        }catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
            System.out.println("Caught it");
        }
    }
}
