package com.zto.sxy.design.strategy;

/**
 * Created by spilledyear on 2017/9/6.
 *
 * 策略模式
 *
 * 策略模式定义了一系列算法，并将每个算法封装起来，使他们可以相互替换，且算法的变化不会影响到使用算法的客户。需要设计一个接口，
 * 为一系列实现类提供统一的方法，多个实现类实现该接口，设计一个抽象类（可有可无，属于辅助类），提供辅助函数。
 *
 * 策略模式的决定权在用户，系统本身提供不同算法的实现，新增或者删除算法，对各种算法做封装。因此，策略模式多用在算法决策系统中，
 * 外部用户只需要决定用哪个算法即可。
 */


/**
 * 假设现在要设计一个贩卖各类书籍的电子商务网站的购物车系统。一个最简单的情况就是把所有货品的单价乘上数量，但是实际情况肯定比这要复杂。比如，
 * 本网站可能对所有的高级会员提供每本20%的促销折扣；对中级会员提供每本10%的促销折扣；对初级会员没有折扣。
 * 根据描述，折扣是根据以下的几个算法中的一个进行的：
 * 算法一：对初级会员没有折扣。
 * 算法二：对中级会员提供10%的促销折扣。
 * 算法三：对高级会员提供20%的促销折扣。
 *
 *
 */
public class Test {
    public static void main(String[] args) {
        //选择并创建需要使用的策略对象
        MemberStrategy strategy = new PrimaryMemberStrategy();

        //创建环境
        Price price= new Price(strategy);
        //计算价格
        double quote = price.quote(300);
        System.out.println("图书的最终价格为"+ quote);
    }
}
