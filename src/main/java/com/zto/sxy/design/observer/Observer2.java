package com.zto.sxy.design.observer;

/**
 * Created by spilledyear on 2017/9/6.
 */
public class Observer2 implements Observer {
    @Override
    public void update() {
        System.out.println("observer2 has received");
    }
}
