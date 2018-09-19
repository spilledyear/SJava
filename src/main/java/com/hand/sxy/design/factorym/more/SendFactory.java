package com.hand.sxy.design.factorym.more;

/**
 * Created by spilledyear on 2017/9/5.
 */
public class SendFactory {

    public ISend produceMail(){
        return new MailSend();
    }

    public ISend produceSms(){
        return new SmsSend();
    }
}
