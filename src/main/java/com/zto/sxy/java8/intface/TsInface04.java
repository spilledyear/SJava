package com.zto.sxy.java8.intface;

@FunctionalInterface
public interface TsInface04 {
    String getName(String firstName);

    default void getMoney() {
        System.out.println("默认方法就是这么任性");
    }

    static void main(String[] args) {
        System.out.println("自从接口可以由静态方法，可以写main函数，作为程序的入口");
    }

}
