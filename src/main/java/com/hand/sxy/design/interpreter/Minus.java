package com.hand.sxy.design.interpreter;

/**
 * Created by spilledyear on 2017/9/11.
 */
public class Minus implements Expression {
    @Override
    public int interpret(Context context) {
        return context.getNumber1() - context.getNumber2();
    }
}
