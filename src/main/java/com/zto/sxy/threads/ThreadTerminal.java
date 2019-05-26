package com.zto.sxy.threads;

import java.util.concurrent.TimeUnit;


public class ThreadTerminal {
    public static void main(String[] args) {
        Thread thread1 = new Thread(new Runner(), "RunnerThread1");
        // to make sure this thread will stop when main thread is stop
        thread1.setDaemon(true);
        thread1.start();

        SleepUtils.second(3);
        // 通过设置中断标识结束线程
        thread1.interrupt();


        Runner runner = new Runner();
        Thread thread2 = new Thread(new Runner(), "RunnerThread2");
        thread2.start();
        SleepUtils.second(5);
        // 通过设置属性的值结束线程
        runner.terminate();
    }


    static class Runner implements Runnable {
        private volatile boolean stoped = false;

        @Override
        public void run() {
            while (!stoped && !Thread.currentThread().isInterrupted()) {
                try {
                    System.out.println(Thread.currentThread().getName() + ": Doing");
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    System.out.println("throw the InterruptedException and the interrupt will be reset because of another thread invoke this thread's interrupt() method \n");
                    // in this way, the interrupt flag will be reset, so we need to set the interrupt flag to true again
//                    Thread.currentThread().interrupt();
                }
            }
        }

        public void terminate() {
            stoped = true;
        }
    }
}
