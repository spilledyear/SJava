package com.hand.sxy.proxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author spilledyear
 */
public class LogInvocationHandler implements InvocationHandler {

    /**
     * the target that we want to be proxy
     */
    private Learn target;

    public LogInvocationHandler(Learn target) {
        this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("执行代理方法之前");

        method.invoke(target, args);

        System.out.println("执行代理方法之后");
        return null;
    }
}
