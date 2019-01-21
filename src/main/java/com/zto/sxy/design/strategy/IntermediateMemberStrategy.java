package com.zto.sxy.design.strategy;

/**
 * Created by spilledyear on 2017/9/6.
 */
public class IntermediateMemberStrategy implements MemberStrategy {
    @Override
    public double calcPrice(double bookPrice) {
        System.out.println("对于中级会员折扣为10%");
        return bookPrice * 0.9;
    }
}
