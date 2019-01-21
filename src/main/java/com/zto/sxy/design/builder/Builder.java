package com.zto.sxy.design.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spilledyear on 2017/9/5.
 */
public class Builder {

    private List<ISend> list = new ArrayList<>();

    //生产MiilSender
    public void produceMailSend(int count){
        for(int i  = 0; i < count; i++){
            list.add(new MailSend());
        }
    }

    //生产 SmsSend
    public void produceSmsSend(int count){
        for(int i = 0; i < count; i++){
            list.add(new SmsSend());
        }
    }
}
