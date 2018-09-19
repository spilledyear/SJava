package com.hand.sxy.design.visitor;

/**
 * Created by spilledyear on 2017/9/11.
 */
public class MyVisitor implements Visitor{
    @Override
    public void visit(Subject subject) {
        System.out.println("visitor the subject "+ subject.getSubject() );
    }
}
