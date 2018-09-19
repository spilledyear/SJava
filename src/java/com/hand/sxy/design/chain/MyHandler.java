package com.hand.sxy.design.chain;

/**
 * Created by spilledyear on 2017/9/6.
 */
public class MyHandler implements Handler{

    private String name;
    private Handler handler;

    public MyHandler(String name){
        this.name = name;
    }

    @Override
    public void operator() {
        System.out.println(name + "deal!");
        if(getHandler() != null){
            getHandler().operator();
        }
    }



    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
