package com.hand.sxy.design.command;

/**
 * Created by spilledyear on 2017/9/11.
 */
public class Invoker {
    private Command command;

    public Invoker(Command command){
        this.command = command;
    }

    public void action(){
        command.exe();

    }

}
