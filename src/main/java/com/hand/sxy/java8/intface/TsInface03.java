package com.hand.sxy.java8.intface;

public interface TsInface03 {
    static void staticMethod(){
        System.out.println("我是 TsInface03 接口中的static方法: staticMethod");
    }

    default void default1(){
        System.out.println("我是 TsInface03 接口中的default方法: default1");
    }

}
