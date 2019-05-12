package com.zto.sxy.base;

/**
 * @author spilledyear
 * @date 2019-02-20 18:15
 */
public class Initialization {
    public static void main(String[] args) {
        System.out.println(num1);
        System.out.println(num2);
    }

    /**
     * 以下代码结果
     * <p>
     * num1 = 1
     * num2 = 20
     * <p>
     * 先执行Father构造函数，然后初始化，因为num1在构造函数中已经初始化了，并且在定义变量的时候没有初始化，而number在定义变量的时候初始化为20，所以结果为 1 20
     */
    private static Initialization initialization = new Initialization();
    public static int num1;
    public static int num2 = 20;


    //构造方法中对于静态变量赋值
    public Initialization() {
        num1++;
        num2++;
        System.out.println("父类无参构造函数执行");
    }


    /**
     * 调换顺序，以下结果
     * <p>
     * num1 = 1
     * num2 = 21
     * <p>
     * 先对初始化num1 num2，然后执行构造函数，所以结果为 1 21
     */
//    public static int num1;
//    public static int num2 = 20;
//    private static Initialization initialization = new Initialization();
//
//
//    //构造方法中对于静态变量赋值
//    public Initialization() {
//        num1++;
//        num2++;
//        System.out.println("父类无参构造函数执行");
//    }

}
