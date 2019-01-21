package com.zto.sxy.design.mediator;

/**
 * Created by spilledyear on 2017/9/11.
 */
public abstract class User {

    private Mediator  mediator;

    private Mediator getMediator(){
        return mediator;
    }

    public User(Mediator mediator){
        this.mediator = mediator;
    }

    public abstract void work();
}
