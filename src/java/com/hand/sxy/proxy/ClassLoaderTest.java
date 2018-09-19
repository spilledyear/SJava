package com.hand.sxy.proxy;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Moxie on 2017/2/16.
 */
public class ClassLoaderTest {
    public static void main(String[] args) {
        try {
            System.out.println("classLoaderTest.getClass().getClassLoader() :" + ClassLoaderTest.class.getClassLoader());

            System.out.println(ClassLoaderTest.class.getClassLoader().getParent());

            Class.forName("com.hand.hsp.ClassLoaderTest", true, ClassLoaderTest.class.getClassLoader().getParent());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClassLoaderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
