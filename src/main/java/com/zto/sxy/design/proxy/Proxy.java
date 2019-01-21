package com.zto.sxy.design.proxy;

/**
 * Created by spilledyear on 2017/9/6.
 */
public class Proxy implements Sourceable {
    private Source source;

    public Proxy(){
        super();
        source = new Source();
    }

    @Override
    public void method() {

        System.out.println("执行方法前");
        source.method();
        System.out.println("执行方法后");
    }
}
