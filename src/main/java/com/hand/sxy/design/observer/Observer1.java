package com.hand.sxy.design.observer;

/**
 * Created by spilledyear on 2017/9/6.
 */
public class Observer1 implements Observer{
    @Override
    public void update() {
        System.out.println("observer1 has received");
    }
}
