package com.zto.sxy.design.decorator;

/**
 * Created by spilledyear on 2017/9/6.
 */
public class Source implements Sourceable {
    @Override
    public void method() {
        System.out.println("this is origin method");
    }
}
