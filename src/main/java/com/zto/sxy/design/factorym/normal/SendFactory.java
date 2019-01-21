package com.zto.sxy.design.factorym.normal;

/**
 * Created by spilledyear on 2017/9/5.
 */
public class SendFactory {
    public ISend send(String type) {
        switch (type) {
            case "mail":
                return new MailSend();
            case "sms":
                return new SmsSend();
            default:
                return null;
        }
    }
}
