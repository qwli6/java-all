package com.lqwit.java.exception;

/**
 * 描述:
 * @author liqiwen
 * @since 2018-05-01 12:03
 */
public class Excese4 {

    public static void f() throws TestException1, TestException2, TestException3{
        System.out.println("throw TestException1");
        throw new TestException1();
    }

    public static void main(String[] args){
        try {
            f();
        } catch (TestException3 testException3) {
            testException3.printStackTrace();
            System.out.println("捕获 TestException3 类型的异常");
        } catch (TestException2 testException2) {
            testException2.printStackTrace();
            System.out.println("捕获 TestException2 类型的异常");
        } catch (TestException1 testException1) {
            testException1.printStackTrace();
            System.out.println("捕获 TestException1 类型的异常");
        }
    }

}
