package com.hand.sxy.design.memento;

/**
 * Created by spilledyear on 2017/9/11.
 *
 * 备忘录模式
 *
 * 主要目的是保存一个对象的某个状态，以便在适当的时候恢复对象，个人觉得叫备份模式更形象些，通俗的讲下：假设有原始类A，A中有各种属性，
 * A可以决定需要备份的属性，备忘录类B是用来存储A的一些内部状态，类C呢，就是一个用来存储备忘录的，且只能存储，不能修改等操作。
 */
public class Test {
    public static void main(String[] args) {

        //创建原始类
        Original original = new Original("egg");

        //创建备忘录
        Storage storage = new Storage(original.createMemento());

        //修改原始类的状态
        System.out.println("初始状态为："+original.getValue());
        original.setValue("ggg");
        System.out.println("修改后的状态为："+original.getValue());

        //回复初始状态
        original.restoreMemento(storage.getMemento());
        System.out.println("修复后的状态："+original.getValue());
    }
}
