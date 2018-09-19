package com.hand.sxy.design.strategy;

/**
 * Created by spilledyear on 2017/9/6.
 */
public class PrimaryMemberStrategy implements MemberStrategy{
    @Override
    public double calcPrice(double bookPrice) {
        System.out.println("对于初级会员没有折扣");
        return bookPrice;
    }
}
