package com.hand.sxy.design.factorya;

/**
 * Created by spilledyear on 2017/9/5.
 */
public class SmsSendFactory implements Factory{
    @Override
    public ISend produce() {
        return new SmsSend();
    }
}
