package com.zto.sxy.jvm.gc;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * java7中 符号引用(Symbols)转移到了native heap;字面量(interned strings)转移到了java heap;类的静态变量(class statics)转移到了java heap
 * java8彻底移除了永久代(PermGen)，具体参考：http://ifeve.com/java-permgen-removed/
 * <p>
 * 运行时常量池导致的内存溢出异常，只能在java1.7之前的版本才能看到效果
 * -XX:PermSize=10M -XX:MaxPermSize=10M
 * <p>
 * 在java8中上面的参数已经没用了，新出了下面的参数
 * -XX:MetaSpaceSize=10M -XX:MaxMetaSpaceSize
 * <p>
 * -Xms10M -Xmx10M
 *
 * @author spilledyear
 * @date 2018/12/12 23:05
 */
public class RuntimeConstantPoolOOM {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        int i = 0;
        while (true) {
            list.add(String.valueOf(i++).intern());
        }
    }

    /**
     * JDK7开始intern()不会再复制实例,只是在常量池中记录首次出现的实例引用
     */
    @Test
    public void testEqual() {
        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1);// true

        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);// false
    }
}
