package com.zto.sxy.jvm.gc;

public class TestEden {
    /**
     * 堆初始化空间和最大空间都为20M，栈空间为10M，Eden和Survivor比为 8:1:1，PrintGCDetails为打印GC执行日志
     * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     */


    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        testAllocation();
    }

    public static void testAllocation() {
        byte[] allocation1, allocation2, allocation3, allocation4;

        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];

        // 触发一次Minor GC
        allocation4 = new byte[4 * _1MB];
    }

}
