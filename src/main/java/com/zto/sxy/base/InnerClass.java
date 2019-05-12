package com.zto.sxy.base;

/**
 * https://www.jianshu.com/p/e310b56fd105
 *
 * @author spilledyear
 * @date 2019-02-22 11:18
 */
public class InnerClass {
    public static void main(String[] args) {
        Outer outer = new Outer();
        outer.localVariable();
        outer.methodVariable(200);

        Outer.Inner io = outer.new Inner();
    }
}


/**
 * https://www.cnblogs.com/chenssy/p/3388487.html
 * https://www.cnblogs.com/dolphin0520/p/3811445.html
 * <p>
 * 在Java中内部类主要分为成员内部类、局部内部类、匿名内部类、静态内部类
 * <p>
 * <p>
 * <p>
 * 1、成员内部类
 * 成员内部类中不能存在任何static的变量和方法；成员内部类是依附于外围类的，所以只有先创建了外围类才能够创建内部类；
 * .this与.new语法：
 * 如果我们需要创建某个内部类对象，必须要利用外部类的对象通过.new来创建内部类；OuterClass.InnerClass innerClass = outerClass.new InnerClass();
 * 如果我们需要生成对外部类对象的引用，可以使用OuterClassName.this，这样就能够产生一个正确引用外部类的引用了；
 * <p>
 * <p>
 * <p>
 * 2、局部内部类
 * 嵌套在方法和作用域内；
 * <p>
 * <p>
 * <p>
 * 3、匿名内部类
 * 匿名内部类是没有访问修饰符；匿名内部类是没有构造方法的，因为它连名字都没有何来构造方法；
 * <p>
 * <p>
 * <p>
 * 4、静态内部类
 * 非静态内部类在编译完成之后会隐含地保存着一个引用，该引用是指向创建它的外围内，但是静态内部类却没有；
 * 静态内部类的创建是不需要依赖于外围类的；
 * 静态内部类不能使用任何外围类的非static成员变量和方法；
 */

class Outer {


    class Inner {
        public void print() {
        }
    }


    public void localVariable() {
        int temp = 100;

        Outer.Inner inner = new Outer.Inner() {
            public void print() {
                System.out.println(temp);
            }
        };
    }


    public void methodVariable(int temp) {
        Outer.Inner inner = new Outer.Inner() {
            public void print() {
                System.out.println(temp);
            }
        };
    }
}


