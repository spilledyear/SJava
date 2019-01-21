package com.zto.sxy.design.observer;

/**
 * Created by spilledyear on 2017/9/6.
 *
 * 观察者模式
 *
 * 观察者模式类似于邮件订阅和RSS订阅，当我们浏览一些博客或wiki时，经常会看到RSS图标，就这的意思是，当你订阅了该文章，如果后续有更新，
 * 会及时通知你。其实，简单来讲就一句话：当一个对象变化时，其它依赖该对象的对象都会收到通知，并且随着变化！对象之间是一种一对多的关系。
 *
 * 用自己的话解释一下这部分代码。有3个观察者：Obserrver、Observer1、Observer2 , 当Observer更新的时候，将其它两个观察者也更新了，再实际
 * 应用中，各个观察者对应不同的实现
 */
public class Test {
    public static void main(String[] args) {
        Subject subject = new MySubject();
        subject.add(new Observer1());
        subject.add(new Observer2());

        subject.operation();
    }
}
