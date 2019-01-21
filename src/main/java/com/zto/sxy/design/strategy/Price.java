package com.zto.sxy.design.strategy;

/**
 * Created by spilledyear on 2017/9/6.
 */
public class Price {

    //持有一个具体的策略对象
    private MemberStrategy strategy;

    public Price(MemberStrategy strategy){
        this.strategy = strategy;
    }

    //计算图书的价格
    public double quote(double bookPrice){
        return strategy.calcPrice(bookPrice);
    }

}
