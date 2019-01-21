package com.zto.sxy.design.factorym.statically;

/**
 * Created by spilledyear on 2017/9/5.
 */
public class SendFactory {

    public static ISend produceMail(){
        return new MailSend();
    }

    public static ISend produceSms(){
        return new SmsSend();
    }
}
