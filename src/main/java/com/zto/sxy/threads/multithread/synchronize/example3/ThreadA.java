package com.zto.sxy.threads.multithread.synchronize.example3;

/**
 * Created by Brian on 2016/4/12.
 */
public class ThreadA extends Thread {
    private MyObject object;

    public ThreadA(MyObject object) {
        super();
        this.object = object;
    }

    @Override
    public void run() {
        super.run();
        object.methodA();
    }
}
