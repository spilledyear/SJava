package com.hand.sxy.design.mediator;

/**
 * Created by spilledyear on 2017/9/11.
 */
public class User1 extends User {

    public User1(Mediator mediator){
        super(mediator);
    }

    @Override
    public void work() {
        System.out.println("user1 exe");
    }
}
