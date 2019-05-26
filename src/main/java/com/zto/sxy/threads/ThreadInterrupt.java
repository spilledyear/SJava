package com.zto.sxy.threads;

import java.util.concurrent.TimeUnit;


/**
 * 有关于线程中断 ，所谓得线程中断，并非真正得中断线程，只是给线程设置一个中断标识为true
 * <p>
 * 在非阻塞的代码快中，中断标识被正确的设置为true
 * 在阻塞(wait()、sleep()、join())代码块中，抛出异常后中断标识会被重置
 * <p>
 * 中断原理请参考： https://juejin.im/post/59157e51da2f60005dd478f8
 */
public class ThreadInterrupt {
    public static void main(String[] args) {
        Thread sleepThread = new Thread(new SleepRunner(), "SleepRunner");
        sleepThread.setDaemon(true);

        Thread busyThread = new Thread(new BusyRunner(), "BusyRunner");
        busyThread.setDaemon(true);

        sleepThread.start();
        busyThread.start();

        SleepUtils.second(5);
        sleepThread.interrupt();
        busyThread.interrupt();

        System.out.println("SleepThread interrupt flag is: " + sleepThread.isInterrupted());
        System.out.println("BusyRunner interrupt flag is: " + busyThread.isInterrupted());
    }


    static class SleepRunner implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    // in this way, the interrupt flag will be reset
                    e.printStackTrace();
                }
            }
        }
    }


    static class BusyRunner implements Runnable {
        @Override
        public void run() {
            while (true) {
            }
        }
    }
}
