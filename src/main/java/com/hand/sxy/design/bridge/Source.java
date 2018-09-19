package com.hand.sxy.design.bridge;

/**
 * Created by spilledyear on 2017/9/6.
 */
public class Source implements Sourceable{
    @Override
    public void method() {
        System.out.println("这是一个方法");
    }
}
