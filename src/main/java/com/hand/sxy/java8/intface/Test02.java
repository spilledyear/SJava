package com.hand.sxy.java8.intface;

public class Test02 implements TsInface01, TsInface02 {
    public static void main(String[] args) {
        // 参数 + 规则 + 方法体
        TsInface04 ts04 = (fisrtName) -> {
            return fisrtName + "bbbbbb";
        };

        // 只有一个参数的时候，参数中的那个括号可以省略
        TsInface04 ts05 = fisrtName -> {
            return fisrtName + "bbbbbb";
        };

        // 只有一句话的方法体，大括号 和 return 也可以省略，在省略大括号的情况下，默认会return
        TsInface04 ts06 = fisrtName -> fisrtName + "bbbbbb";

        System.out.println(ts04.getName("全写"));
        System.out.println(ts05.getName("简写"));
        System.out.println(ts06.getName("简简写"));

        // 传一个方法体，替换匿名内部类
        System.out.println(test(firstName -> firstName + "aaa"));
    }

    public static String test(TsInface04 t4){
        return t4.getName("什么鬼");
    }
}
