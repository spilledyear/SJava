package com.hand.sxy.java8.intface;


import java.util.HashMap;
import java.util.Map;

@FunctionalInterface
interface TsInface05 {
    String getName(String firstName);
    default void getMoney() {}
}

@FunctionalInterface
interface TsInface06 {
    Map<String, String> getName(Map<String,String> map, String firstName);
}

@FunctionalInterface
interface TsInface07 {
    void getName(Map<String,String> map, String firstName);
}

public class Test03 {
    public Test03(){

    }
    public Test03(Map<String,String> map, String firstName){
        map.put("NAME", firstName);
        System.out.println(map.get("NAME"));
    }

    public static void main(String[] args) {
        Test03 test03 = new Test03();

        // 引用实例的方法
        TsInface05 t501 = test03::getName1;
        t501.getName("引用实例的方法");

        // 引用类的方法
        TsInface05 t502 = Test03::getName2;
        t501.getName("引用类的方法");

        // 引用类实例方法
        Map<String, String> map = new HashMap<>(16);
        TsInface06 t601 = Test03::getName3;
        t601.getName(map, "引用类实例方法");

        // 引用构造方法
        TsInface07 t701 = Test03::new;
        t701.getName(map, "引用构造方法");
    }


    // 实例方法
    public String getName1(String str) {
        System.out.println(str);
        return str;
    }

    // 类方法
    public static String getName2(String str) {
        System.out.println(str);
        return str;
    }

    // 类实例方法
    public static Map<String, String> getName3(Map<String, String> map, String str) {
        map.put("NAME", str);
        System.out.println(map.get("NAME"));
        return map;
    }
}
