package com.hand.sxy.design.memento;

/**
 * Created by spilledyear on 2017/9/11.
 */
public class Memento {

    private String value;

    public Memento(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
