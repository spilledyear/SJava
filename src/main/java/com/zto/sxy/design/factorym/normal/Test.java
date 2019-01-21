package com.zto.sxy.design.factorym.normal;

/**
 * Created by spilledyear on 2017/9/5.
 */

/**
 * 普通工厂方法模式
 *
 * 创建一个工厂类，对实现了统一接口的不同类进行实例的创建
 */
public class Test {

    public static void main(String[] args){
        SendFactory factory = new SendFactory();
        ISend send = factory.send("mail");
        send.send("呵呵哒");
    }
}
