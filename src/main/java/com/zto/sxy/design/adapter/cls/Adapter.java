package com.zto.sxy.design.adapter.cls;

/**
 * Created by spilledyear on 2017/9/6.
 */
public class Adapter extends Source implements Targetable{
    @Override
    public void method2() {
        System.out.println("this is targetable method");
    }
}
