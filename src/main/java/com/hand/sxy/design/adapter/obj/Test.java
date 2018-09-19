package com.hand.sxy.design.adapter.obj;

/**
 * Created by spilledyear on 2017/9/6.
 *
 * 适配器模式 --- 对象的是适配器模式
 *
 */
public class Test {
    public static void main(String[] args) {
        Source source = new Source();
        Targetable target = new Adapter(source);
        target.method1();
        target.method1();
    }
}
