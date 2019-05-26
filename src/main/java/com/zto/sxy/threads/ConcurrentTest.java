package com.zto.sxy.threads;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentTest {
    static CountDownLatch countDown = new CountDownLatch(1);

    public static void main(String[] args) {
        // ThreadCount=10 long  不到5千万； Long 4千万左右； long加上 volatile 修饰，结果稍微少一点；
//        synchronizer();

        // ThreadCount=10 AtomicLong 8千万左右;
//        synchronizer2();

        // ThreadCount=10 ReentrantLock 9千万左右
//        lock();

        // 2亿多， 单线程这么猛
//        single();
    }

    static int ThreadCount = 1;

    public static void single() {
        Thread t = new Thread(() -> {
            while (true) {
                count++;
            }
        });
        t.setDaemon(true);
        t.start();
        SleepUtils.second(3);
        System.out.println(Thread.currentThread().getName() + ":  " + count);
    }

    // 使用 Long 和 long 结果偏差 大概 1000W 左右， 加上volatile 稍有影响
    static volatile long count = Long.valueOf(0);

    public static void synchronizer() {
        for (int i = 0; i < ThreadCount; i++) {
            Thread thread = new Thread(() -> {
                try {
                    countDown.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                while (true) {
                    synchronized (ConcurrentTest.class) {
                        count++;
                    }
                    if (count % 10000000 == 0) {
                        System.out.println(Thread.currentThread().getName() + ":  " + count);
                    }
                }
            }, "Thread-" + i);

            thread.setDaemon(true);
            thread.start();
        }

        countDown.countDown();
        SleepUtils.second(3);

        System.out.println(Thread.currentThread().getName() + ":  " + count);
    }


    static AtomicLong atomicLong = new AtomicLong();

    public static void synchronizer2() {
        for (int i = 0; i < ThreadCount; i++) {
            Thread thread = new Thread(() -> {
                try {
                    countDown.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                while (true) {
                    atomicLong.incrementAndGet();
                    if (atomicLong.get() % 10000000 == 0) {
                        System.out.println(Thread.currentThread().getName() + ":  " + atomicLong.get());
                    }
                }
            }, "Thread-" + i);

            thread.setDaemon(true);
            thread.start();
        }

        countDown.countDown();
        SleepUtils.second(3);

        System.out.println(Thread.currentThread().getName() + ":  " + atomicLong.get());
    }


    static ReentrantLock lock = new ReentrantLock();

    public static void lock() {
        for (int i = 0; i < ThreadCount; i++) {
            Thread thread = new Thread(() -> {
                try {
                    countDown.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                while (true) {
                    lock.lock();
                    try {
                        count++;
                    } finally {
                        lock.unlock();
                    }

                    if (count % 10000000 == 0) {
                        System.out.println(Thread.currentThread().getName() + ":  " + count);
                    }
                }
            }, "Thread-" + i);

            thread.setDaemon(true);
            thread.start();
        }

        countDown.countDown();
        SleepUtils.second(3);

        System.out.println(Thread.currentThread().getName() + ":  " + count);
    }

}
