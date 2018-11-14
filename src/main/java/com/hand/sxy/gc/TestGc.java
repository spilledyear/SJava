package com.hand.sxy.gc;

public class TestGc {
    public Object instance = null;

    private static final int _1MB = 1024 * 1024;

    private byte[] bigSize = new byte[2 * _1MB];

    public static void main(String[] args) {
        TestGc testA = new TestGc();
        TestGc testB = new TestGc();

        testA.instance = testB;
        testB.instance = testA;

        testA = null;
        testB = null;

        System.gc();
    }
}
