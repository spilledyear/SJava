package com.zto.sxy.design.observer;

/**
 * Created by spilledyear on 2017/9/6.
 */
public interface Subject {

    //增加观察者
    void add(Observer observer);

    //删除观察者
    void remove(Observer observer);

    //通知使所有的观察者
    void notifyObservers();

    //自身的操哦做
    void operation();

}
