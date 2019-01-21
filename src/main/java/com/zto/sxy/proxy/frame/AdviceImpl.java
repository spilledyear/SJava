package com.zto.sxy.proxy.frame;

/**
 * Created by Moxie on 2017/2/20.
 */
public class AdviceImpl implements Advice{
    public void before() {
        System.out.println("执行方法前");
    }

    public void after() {
        System.out.println("执行方法后");
    }
}
