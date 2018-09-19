package com.hand.sxy.design.decorator;

/**
 * Created by spilledyear on 2017/9/6.
 */
public class Decorator implements Sourceable {

    private Source source;

    public Decorator(Source source){
        this.source = source;
    }

    @Override
    public void method() {
        System.out.println("执行方法前装饰一下");

        source.method();

        System.out.println("执行方法后装饰一下");
    }
}
