package com.zto.sxy.spring.test;

import com.zto.sxy.spring.tx.TxConfig;
import com.zto.sxy.spring.tx.UserService2;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IOCTest_Tx {

    @Test
    public void test01() {
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(TxConfig.class);

        UserService2 userService = applicationContext.getBean(UserService2.class);

        userService.insertUser();
        applicationContext.close();
    }

}
