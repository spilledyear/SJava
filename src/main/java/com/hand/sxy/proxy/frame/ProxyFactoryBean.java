package com.hand.sxy.proxy.frame;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Moxie on 2017/2/20.
 */
public class ProxyFactoryBean {

    private Advice advice;
    private Object target;

    public Object getProxy(){
        Object proxy = Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        advice.before();
                        method.invoke(target,args);
                        advice.after();
                        return null;
                    }
                }
        );
        return proxy;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    public void setTarget(Object target) {
        this.target = target;
    }
}
