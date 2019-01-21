package com.zto.sxy.design.iterator;


/**
 * Created by spilledyear on 2017/9/6.
 */
public interface Iterator {

    //前移
    Object previous();

    //后移
    Object next();

    //是否还有后一个元素
    boolean hasNext();

    Object first();

}
