package com.zto.sxy.jvm.gc;

import java.util.ArrayList;
import java.util.List;

/**
 * 堆内存溢出异常测试
 * -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=D:\data\gc.hprof
 *
 * @author spilledyear
 * @date 2018/12/12 21:00
 */
public class HeepOOM {
    static class OOMObejct {

    }

    public static void main(String[] args) {
        List<OOMObejct> list = new ArrayList<>();
        while (true) {
            list.add(new OOMObejct());
        }
    }
}
