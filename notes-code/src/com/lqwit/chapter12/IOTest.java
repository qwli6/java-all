package com.lqwit.chapter12;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class IOTest {

    private static PrintWriter writer;

    public static void main(String[] args){
        writeText();
//        readText();
    }

    private static void writeText() {
        try {
            writer = new PrintWriter("temp.txt");
            writer.print("hello kotlin");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            writer.close();
        }
    }

    private static void readText(){
        //等待控制台输入
//        Scanner scanner =new Scanner(System.in);

        try {
            Scanner scanner =new Scanner(new File("temp.txt"));
            System.out.println(scanner.nextLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
