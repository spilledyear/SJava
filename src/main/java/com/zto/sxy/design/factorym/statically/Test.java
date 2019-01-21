package com.zto.sxy.design.factorym.statically;

/**
 * Created by spilledyear on 2017/9/5.
 */

/**
 * 静态工厂方法模式
 *
 * 将多个工厂方法模式改成静态的，这样工厂类不用创建
 */
public class Test {

    public static void main(String[] args){
        ISend send = SendFactory.produceMail();
        send.send("呵呵哒");
    }
}
