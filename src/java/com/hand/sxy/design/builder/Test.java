package com.hand.sxy.design.builder;

/**
 * Created by spilledyear on 2017/9/5.
 *
 * 建造者模式
 * 工厂类模式提供的是创建单个类的模式，而建造者模式则是将各种产品集中起来进行管理，用来创建复合对象，其实就是对一系列操作的封装
 */
public class Test {
    public static void main(String[] args){
        Builder builder = new Builder();
        builder.produceMailSend(5);
    }
}
