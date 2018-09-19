package com.hand.sxy.design.iterator;

/**
 * Created by spilledyear on 2017/9/6.
 *
 * 迭代子模式（Iterator）
 *
 * 这个没啥好讲的，可以去看看java中的集合源码
 */
public class Test {
    public static void main(String[] args) {
        Collection collection = new MyCollection();
        Iterator iterator = collection.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next()  );
        }
    }
}
