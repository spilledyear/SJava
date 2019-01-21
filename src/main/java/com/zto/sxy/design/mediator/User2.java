package com.zto.sxy.design.mediator;

/**
 * Created by spilledyear on 2017/9/11.
 */
public class User2 extends User {

    public User2(Mediator mediator){
        super(mediator);
    }

    @Override
    public void work() {
        System.out.println("user2 exe");
    }
}
