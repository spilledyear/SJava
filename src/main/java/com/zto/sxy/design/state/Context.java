package com.zto.sxy.design.state;


/**
 * Created by spilledyear on 2017/9/11.
 */
public class Context {

    private State state;

    public Context(State state){
        this.state = state;
    }

    public void method(){
        if(state.getValue().equals("state1")){
            state.method1();;
        }else  if(state.getValue().equals("state2")){
            state.method2();
        }
    }
}
