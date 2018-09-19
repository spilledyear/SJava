package com.hand.sxy.design.adapter.obj;

/**
 * Created by spilledyear on 2017/9/6.
 */
public class Adapter implements Targetable{

    private Source source;

    public Adapter(Source source){
        super();
        this.source = source;
    }

    @Override
    public void method1() {
        source.method1();
    }

    @Override
    public void method2() {

    }
}
