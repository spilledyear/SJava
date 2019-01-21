package com.zto.sxy.design.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spilledyear on 2017/9/6.
 */
public abstract class AbstractSubject implements Subject{

    private List<Observer> list = new ArrayList<>();

    @Override
    public void add(Observer observer) {
        list.add(observer);
    }

    @Override
    public void remove(Observer observer) {
        list.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for(int i = 0; i < list.size(); i++){
            list.get(i).update();
        }
    }

}
