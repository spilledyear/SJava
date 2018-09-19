package com.hand.sxy.design.strategy;

/**
 * Created by spilledyear on 2017/9/6.
 */
public class AdvancedMemberStrategy implements MemberStrategy {
    @Override
    public double calcPrice(double bookPrice) {
        System.out.println("对于高级会员折扣为20%");
        return bookPrice * 0.8;
    }
}
