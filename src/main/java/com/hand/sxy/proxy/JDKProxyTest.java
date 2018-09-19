package com.hand.sxy.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Collection;

/**
 * @author spilledyear
 */
public class JDKProxyTest {
    public static void main(String[] args) {
        // 允许生成代理类的class文件
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        Learn learnReal = new LearnImpl();

        InvocationHandler handler = new LogInvocationHandler(learnReal);

        Learn learn = (Learn) Proxy.newProxyInstance(
                learnReal.getClass().getClassLoader(),
                learnReal.getClass().getInterfaces(),
                handler);

        System.out.println(learn.getClass().getName());

        learn.learnChinese();

//        Class clazzProxy = Proxy.getProxyClass(Collection.class.getClassLoader(), Collection.class);
//        System.out.println(clazzProxy);
    }
}
