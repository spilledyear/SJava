package com.hand.sxy.design.iterator;

/**
 * Created by spilledyear on 2017/9/6.
 */
public interface Collection {

    //获取 iterator
    Iterator iterator();

    //获取集合元素
    Object get(int index);

    //获取元素size
    int size();

}
