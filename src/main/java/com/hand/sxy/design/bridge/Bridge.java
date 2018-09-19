package com.hand.sxy.design.bridge;

/**
 * Created by spilledyear on 2017/9/6.
 */
public abstract class Bridge {
    private Sourceable source;

    public void method(){
        source.method();
    }

    public Sourceable getSource(){
        return source;
    }

    public void setSource(Sourceable source){
        this.source = source;
    }
}
