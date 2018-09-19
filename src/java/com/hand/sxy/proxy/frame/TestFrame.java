package com.hand.sxy.proxy.frame;

import java.io.InputStream;
import java.util.Collection;

/**
 * Created by Moxie on 2017/2/20.
 */
public class TestFrame {
    public static void main(String[] args){
        InputStream is = TestFrame.class.getResourceAsStream("config.properties");
        Object bean = new BeanFactory(is).getBean("xxx");

        System.out.println(bean.getClass().getName());
        ((Collection) bean).clear();
    }

}
