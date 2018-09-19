package com.hand.sxy.proxy.frame;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Moxie on 2017/2/20.
 */
public class BeanFactory {
    Properties prop = new Properties();
    public BeanFactory(InputStream ism){
        try{
            prop.load(ism);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public Object getBean(String name){
        String className  = prop.getProperty(name);
        Object bean = null;
        try{
            Class clazz = Class.forName(className);
            bean = clazz.newInstance();
        }catch(Exception e){
           e.printStackTrace();
        }

        if(bean instanceof ProxyFactoryBean){
            ProxyFactoryBean proxyBean = (ProxyFactoryBean)bean;
            Advice advice = null;
            Object target = null;
            try{
                advice = (Advice)Class.forName(prop.getProperty(name+".advice")).newInstance();
                target = Class.forName(prop.getProperty(name+".target")).newInstance();
                proxyBean.setAdvice(advice);
                proxyBean.setTarget(target);
                Object proxy = ((ProxyFactoryBean) bean).getProxy();
                return proxy;
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return bean;
    }

}
