package com.zto.sxy.jvm.gc;

/**
 * 递归调用导致StackOverflowError，栈内存
 * -Xss180k
 *
 * @author spilledyear
 * @date 2018/12/12 21:30
 */
public class JavaVMStackSOF {
    private int stackLength = 1;

    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) {
        JavaVMStackSOF oom = new JavaVMStackSOF();
        try {
            oom.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length :" + oom.stackLength);
            e.printStackTrace();
        }
    }
}
