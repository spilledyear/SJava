package com.zto.sxy.jvm.gc;

/**
 * 创建线程导致内存溢出异常
 * -Xms20M -Xmx20M -Xss2M
 *
 * @author spilledyear
 * @date 2018/12/12 22:00
 */
public class JavaVMStackOOM {
    private void dontStop() {
        while (true) {

        }
    }

    public void stackLengthByThread() {
        while (true) {
            new Thread(() -> dontStop()).start();
        }
    }

    public static void main(String[] args) {
        JavaVMStackOOM oom = new JavaVMStackOOM();
        oom.stackLengthByThread();
    }
}
