package com.zto.sxy.design.interpreter;

/**
 * Created by spilledyear on 2017/9/11.
 */
public class Plus implements Expression {
    @Override
    public int interpret(Context context) {
        return context.getNumber1() + context.getNumber2();
    }
}
