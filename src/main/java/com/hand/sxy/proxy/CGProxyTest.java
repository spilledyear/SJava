package com.hand.sxy.proxy;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;

/**
 * @author spilledyear
 */
public class CGProxyTest {
    public static void main(String[] args) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "com/sun/proxy");

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(LearnImpl.class);
        enhancer.setCallback(new LogMethodInterceptor());

        Learn realLearn = (LearnImpl) enhancer.create();

        realLearn.learnChinese();
        System.out.println(realLearn);
    }
}
