package com.zto.sxy.design.bridge;

/**
 * Created by spilledyear on 2017/9/6.
 */
public class MyBridge extends Bridge {
    public void method(){
        getSource().method();
    }
}
