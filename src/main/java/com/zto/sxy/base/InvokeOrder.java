package com.zto.sxy.base;

/**
 * @author spilledyear
 * @date 2019-02-22 9:12
 */
public class InvokeOrder {
    public static void main(String[] args) {
        Son son = new Son();
    }
}


class Father {
    protected static int staticCount = 10;

    private int count = 10;


    public Father() {
        System.out.println("父类无参构造函数执行");
    }


    {
        System.out.println("父类成员属性 count 初始化: " + count);
    }

    static {
        System.out.println("父类静态属性 staticCount 初始化: " + staticCount);
    }


    protected static int staticCount2 = 10;

    private int count2 = 10;

}


class Son extends Father {
    static int staticCountSon = 20;

    int countSon = 20;

    static {
        System.out.println("子类的静态属性staticCountSon初始化:" + staticCountSon);
    }

    {
        System.out.println("子类成员属性 countSon 初始化: " + countSon);
    }

    public Son() {
        System.out.println("子类无参构造函数执行");
    }
}


/**
 * <p>
 * 【静态代码块和静态变量初始化执行顺序完全是根据代码的顺序来执行的，从上往下依次执行；同理，成员变量和非静态代码块类似】
 * <p>
 * 1、执行父类的静态代码块、静态变量初始化；
 * 2、执行子类的静态代码块、静态变量初始化；
 * 3、执行父类的非静态代码块，成员变量初始化；
 * 4、执行父类构造方法；
 * 5、执行子类的非静态代码块，成员变量初始化；
 * 6、执行子类构造函数；
 */


