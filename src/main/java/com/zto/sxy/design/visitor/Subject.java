package com.zto.sxy.design.visitor;

/**
 * Created by spilledyear on 2017/9/11.
 */
public interface Subject {
    void accept(Visitor visitor);

    String getSubject();

}
