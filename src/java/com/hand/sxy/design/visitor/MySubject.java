package com.hand.sxy.design.visitor;

/**
 * Created by spilledyear on 2017/9/11.
 */
public class MySubject implements Subject{


    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String getSubject() {
        return "love";
    }
}
