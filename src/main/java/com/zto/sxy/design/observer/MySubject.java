package com.zto.sxy.design.observer;

/**
 * Created by spilledyear on 2017/9/6.
 */
public class MySubject extends AbstractSubject {
    @Override
    public void operation() {
        System.out.println("update self");
        notifyObservers();
    }
}
