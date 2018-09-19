package com.hand.sxy.design.factorym.more;

/**
 * Created by spilledyear on 2017/9/5.
 */

/**
 * 多个工厂方法模式
 *
 * 是对普通工厂方法模式的改进，在普通工厂方法模式中，如果传递的字符串出错，则不能正确的创建对象；
 * 而多个工厂方法模式是提供多个方法，分别创建对象。
 */
public class Test {

    public static void main(String[] args){
        SendFactory factory = new SendFactory();
        ISend send = factory.produceMail();
        send.send("呵呵哒");
    }
}
