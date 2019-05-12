package com.zto.sxy.base;

import java.util.Scanner;

/**
 * Created by spilledyear on 2017/8/29.
 */
public class CustomException extends Exception {
    public CustomException() {
        super();
    }

    public CustomException(String error) {
        super(error);
    }
}

class Test {
    public static void main(String[] args) {
        while (true) {
            try {
                Scanner sc = new Scanner(System.in);
                int number = sc.nextInt();
                check(number);
            } catch (CustomException e) {
                e.printStackTrace();
//                System.exit(0);
            }
        }
    }


    public static void check(int number) throws CustomException {
        if (number < 0) {
            throw new CustomException("不可以小于零，谢谢");
        } else if (number > 100) {
            throw new CustomException("不可以大于100，谢谢");
        }
    }
}
