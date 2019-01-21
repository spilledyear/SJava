package com.zto.sxy.design.command;

/**
 * Created by spilledyear on 2017/9/11.
 */
public class MyCommand implements Command{
    private Receiver receiver;

    public MyCommand(Receiver receiver){
        this.receiver = receiver;
    }


    @Override
    public void exe() {
        receiver.action();
    }
}
