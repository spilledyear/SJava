package com.hand.sxy.design.adapter.cls;

/**
 * Created by spilledyear on 2017/9/6.
 *
 * 适配器模式 ---  类的适配器模式
 * 核心思想就是：有一个Source类，拥有一个方法，待适配，目标接口时Targetable，通过Adapter类，将Source的功能扩展到Targetable里
 */
public class Test {

    public static void main(String[] args){
        Targetable target = new Adapter();
        target.method1();
        target.method2();
    }
}
