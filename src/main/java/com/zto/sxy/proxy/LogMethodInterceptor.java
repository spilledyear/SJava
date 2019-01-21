package com.zto.sxy.proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;


import java.lang.reflect.Method;

public class LogMethodInterceptor implements MethodInterceptor {
    public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("执行代理方法之前");


        Object invoke = methodProxy.invokeSuper(object, args);

        System.out.println("执行代理方法之后");

        return invoke;
    }
}
