package com.zto.sxy.design.bridge;

/**
 * Created by spilledyear on 2017/9/6.
 *
 * 桥接模式
 *
 * 模式 之间的 界限感觉很难分清楚。这个和 对象适配器模式不是很像吗？我觉得 不同的地方就在于适配器模式中内置的对象是一个 对象，而桥接模式中
 * 内置的对象是一个接口，是接口 就可以满足 改革中不同的情况。
 *
 * 桥接模式就是把事物和其具体实现分开，使他们可以各自独立的变化。桥接的用意是：将抽象化与实现化解耦，使得二者可以独立变化，
 * 像我们常用的JDBC桥DriverManager一样，JDBC进行连接数据库的时候，在各个数据库之间进行切换，基本不需要动太多的代码，甚至丝毫不用动，
 * 原因就是JDBC提供统一接口，每个数据库提供各自的实现，用一个叫做数据库驱动的程序来桥接就行了。
 */
public class Test {
    public static void main(String[] args) {
        Bridge bridge = new MyBridge();
        Sourceable source = new Source();
        bridge.setSource(source);
        bridge.method();
    }
}
