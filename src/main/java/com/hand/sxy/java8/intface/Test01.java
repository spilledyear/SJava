package com.hand.sxy.java8.intface;

public class Test01 implements TsInface01, TsInface02 {
    public static void main(String[] args) {
        Test01 t01 = new Test01();
        t01.default1();
    }

    @Override
    public void default1(){
        TsInface02.super.default1();
    }
}
