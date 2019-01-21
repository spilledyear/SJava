package com.zto.sxy.design.interpreter;

/**
 * Created by spilledyear on 2017/9/11.
 *
 * 解释器模式
 *
 * 一般主要应用在OOP开发中的编译器的开发中，所以适用面比较窄
 */
public class Test {
    public static void main(String[] args) {
        int result = new Minus().interpret((new Context(new Plus().interpret(new Context(9, 2)),8)));

        System.out.println(result);
    }
}
