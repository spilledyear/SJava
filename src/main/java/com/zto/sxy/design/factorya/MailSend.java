package com.zto.sxy.design.factorya;

/**
 * Created by spilledyear on 2017/9/5.
 */
public class MailSend implements ISend {
    @Override
    public void send(String message) {
        System.out.println("发送邮件"+ message);
    }
}
