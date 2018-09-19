package com.hand.sxy.design.iterator;

/**
 * Created by spilledyear on 2017/9/6.
 */
public class MyCollection implements Collection {

    private String[]  str = {"A","B","C"};

    @Override
    public Iterator iterator() {
        return new MyIterator(this);
    }

    @Override
    public Object get(int index) {
        return str[index];
    }

    @Override
    public int size() {
        return str.length;
    }
}
