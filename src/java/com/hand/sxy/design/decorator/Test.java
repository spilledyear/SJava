package com.hand.sxy.design.decorator;

/**
 * Created by spilledyear on 2017/9/6.
 *
 * 装饰器模式
 *
 * 这种模式一开始的时候我觉得和代理模式好像，其实还是有点不同。代理模式一般是创建一个对象，但是这种模式是将 原始对象传进去，最终调用的
 * 还是原始对象的方法
 */
public class Test {
    public static void main(String[] args) {
        Source source = new Source();
        Sourceable decorator = new Decorator(source);
        decorator.method();
    }
}
