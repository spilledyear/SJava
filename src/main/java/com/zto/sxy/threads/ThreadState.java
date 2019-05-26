package com.zto.sxy.threads;

public class ThreadState {
    public static void main(String[] args) {
        new Thread(new TimeWaiting(), "TimeWaitingThread").start();
        new Thread(new Waiting(), "WaitingThread").start();

        new Thread(new Blocked(), "BlockedThread-1").start();
        new Thread(new Blocked(), "BlockedThread-2").start();
    }


    static class TimeWaiting implements Runnable {
        @Override
        public void run() {
            while (true) {
                SleepUtils.second(100);
            }
        }
    }


    static class Waiting implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (Waiting.class) {
                    try {
                        Waiting.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    static class Blocked implements Runnable {
        @Override
        public void run() {
            synchronized (Blocked.class) {
                while (true) {
                    SleepUtils.second(100);
                }
            }
        }
    }


    /**
     * 1、通过 jps 找到对用的JVM进程号
     * 2、执行 jstack 进程号 查看线程信息
     *
     *
     *
     * 2019-05-26 14:06:21
     * Full thread dump Java HotSpot(TM) 64-Bit Server VM (11.0.3+12-LTS mixed mode):
     *
     * Threads class SMR info:
     * _java_thread_list=0x00000275822bded0, length=17, elements={
     * 0x00000275ff583000, 0x00000275ff58f000, 0x00000275ff5ed800, 0x00000275ff5ef000,
     * 0x00000275ff5f1000, 0x00000275ff5f4800, 0x00000275ff600000, 0x00000275ff560800,
     * 0x00000275ff87a000, 0x00000275ff880800, 0x00000275ff881800, 0x00000275820d2000,
     * 0x000002758221d800, 0x000002758221e000, 0x000002758221f800, 0x0000027582220000,
     * 0x00000275db4a7800
     * }
     *
     * "Reference Handler" #2 daemon prio=10 os_prio=2 cpu=0.00ms elapsed=30.71s tid=0x00000275ff583000 nid=0x3ee8 waiting on condition  [0x000000ea838ff000]
     *    java.lang.Thread.State: RUNNABLE
     *         at java.lang.ref.Reference.waitForReferencePendingList(java.base@11.0.3/Native Method)
     *         at java.lang.ref.Reference.processPendingReferences(java.base@11.0.3/Reference.java:241)
     *         at java.lang.ref.Reference$ReferenceHandler.run(java.base@11.0.3/Reference.java:213)
     *
     * "Finalizer" #3 daemon prio=8 os_prio=1 cpu=0.00ms elapsed=30.71s tid=0x00000275ff58f000 nid=0x470c in Object.wait()  [0x000000ea839fe000]
     *    java.lang.Thread.State: WAITING (on object monitor)
     *         at java.lang.Object.wait(java.base@11.0.3/Native Method)
     *         - waiting on <0x0000000711708f10> (a java.lang.ref.ReferenceQueue$Lock)
     *         at java.lang.ref.ReferenceQueue.remove(java.base@11.0.3/ReferenceQueue.java:155)
     *         - waiting to re-lock in wait() <0x0000000711708f10> (a java.lang.ref.ReferenceQueue$Lock)
     *         at java.lang.ref.ReferenceQueue.remove(java.base@11.0.3/ReferenceQueue.java:176)
     *         at java.lang.ref.Finalizer$FinalizerThread.run(java.base@11.0.3/Finalizer.java:170)
     *
     * "Signal Dispatcher" #4 daemon prio=9 os_prio=2 cpu=0.00ms elapsed=30.69s tid=0x00000275ff5ed800 nid=0x29cc runnable  [0x0000000000000000]
     *    java.lang.Thread.State: RUNNABLE
     *
     * "Attach Listener" #5 daemon prio=5 os_prio=2 cpu=0.00ms elapsed=30.68s tid=0x00000275ff5ef000 nid=0x32f4 waiting on condition  [0x0000000000000000]
     *    java.lang.Thread.State: RUNNABLE
     *
     * "C2 CompilerThread0" #6 daemon prio=9 os_prio=2 cpu=78.13ms elapsed=30.68s tid=0x00000275ff5f1000 nid=0x3590 waiting on condition  [0x0000000000000000]
     *    java.lang.Thread.State: RUNNABLE
     *    No compile task
     *
     * "C1 CompilerThread0" #9 daemon prio=9 os_prio=2 cpu=78.13ms elapsed=30.68s tid=0x00000275ff5f4800 nid=0x3df8 waiting on condition  [0x0000000000000000]
     *    java.lang.Thread.State: RUNNABLE
     *    No compile task
     *
     * "Sweeper thread" #10 daemon prio=9 os_prio=2 cpu=0.00ms elapsed=30.68s tid=0x00000275ff600000 nid=0xecc runnable  [0x0000000000000000]
     *    java.lang.Thread.State: RUNNABLE
     *
     * "Common-Cleaner" #11 daemon prio=8 os_prio=1 cpu=0.00ms elapsed=30.60s tid=0x00000275ff560800 nid=0x4e80 in Object.wait()  [0x000000ea83fff000]
     *    java.lang.Thread.State: TIMED_WAITING (on object monitor)
     *         at java.lang.Object.wait(java.base@11.0.3/Native Method)
     *         - waiting on <0x000000071164b868> (a java.lang.ref.ReferenceQueue$Lock)
     *         at java.lang.ref.ReferenceQueue.remove(java.base@11.0.3/ReferenceQueue.java:155)
     *         - waiting to re-lock in wait() <0x000000071164b868> (a java.lang.ref.ReferenceQueue$Lock)
     *         at jdk.internal.ref.CleanerImpl.run(java.base@11.0.3/CleanerImpl.java:148)
     *         at java.lang.Thread.run(java.base@11.0.3/Thread.java:834)
     *         at jdk.internal.misc.InnocuousThread.run(java.base@11.0.3/InnocuousThread.java:134)
     *
     * "JDWP Transport Listener: dt_socket" #12 daemon prio=10 os_prio=0 cpu=15.63ms elapsed=30.54s tid=0x00000275ff87a000 nid=0x4d68 runnable  [0x0000000000000000]
     *    java.lang.Thread.State: RUNNABLE
     *
     * "JDWP Event Helper Thread" #13 daemon prio=10 os_prio=0 cpu=15.63ms elapsed=30.54s tid=0x00000275ff880800 nid=0x3f6c runnable  [0x0000000000000000]
     *    java.lang.Thread.State: RUNNABLE
     *
     * "JDWP Command Reader" #14 daemon prio=10 os_prio=0 cpu=0.00ms elapsed=30.54s tid=0x00000275ff881800 nid=0x4020 runnable  [0x0000000000000000]
     *    java.lang.Thread.State: RUNNABLE
     *
     * "Service Thread" #15 daemon prio=9 os_prio=0 cpu=0.00ms elapsed=30.42s tid=0x00000275820d2000 nid=0x700 runnable  [0x0000000000000000]
     *    java.lang.Thread.State: RUNNABLE
     *
     * "TimeWaitingThread" #16 prio=5 os_prio=0 cpu=0.00ms elapsed=30.41s tid=0x000002758221d800 nid=0x3c84 waiting on condition  [0x000000ea845fe000]
     *    java.lang.Thread.State: TIMED_WAITING (sleeping)
     *         at java.lang.Thread.sleep(java.base@11.0.3/Native Method)
     *         at java.lang.Thread.sleep(java.base@11.0.3/Thread.java:339)
     *         at java.util.concurrent.TimeUnit.sleep(java.base@11.0.3/TimeUnit.java:446)
     *         at com.zto.sxy.threads.SleepUtils.second(SleepUtils.java:8)
     *         at com.zto.sxy.threads.ThreadState$TimeWaiting.run(ThreadState.java:17)
     *         at java.lang.Thread.run(java.base@11.0.3/Thread.java:834)
     *
     * "WaitingThread" #17 prio=5 os_prio=0 cpu=0.00ms elapsed=30.41s tid=0x000002758221e000 nid=0x4374 in Object.wait()  [0x000000ea846ff000]
     *    java.lang.Thread.State: WAITING (on object monitor)
     *         at java.lang.Object.wait(java.base@11.0.3/Native Method)
     *         - waiting on <0x0000000711191230> (a java.lang.Class for com.zto.sxy.threads.ThreadState$Waiting)
     *         at java.lang.Object.wait(java.base@11.0.3/Object.java:328)
     *         at com.zto.sxy.threads.ThreadState$Waiting.run(ThreadState.java:29)
     *         - waiting to re-lock in wait() <0x0000000711191230> (a java.lang.Class for com.zto.sxy.threads.ThreadState$Waiting)
     *         at java.lang.Thread.run(java.base@11.0.3/Thread.java:834)
     *
     * "BlockedThread-1" #18 prio=5 os_prio=0 cpu=0.00ms elapsed=30.41s tid=0x000002758221f800 nid=0x1354 waiting on condition  [0x000000ea847ff000]
     *    java.lang.Thread.State: TIMED_WAITING (sleeping)
     *         at java.lang.Thread.sleep(java.base@11.0.3/Native Method)
     *         at java.lang.Thread.sleep(java.base@11.0.3/Thread.java:339)
     *         at java.util.concurrent.TimeUnit.sleep(java.base@11.0.3/TimeUnit.java:446)
     *         at com.zto.sxy.threads.SleepUtils.second(SleepUtils.java:8)
     *         at com.zto.sxy.threads.ThreadState$Blocked.run(ThreadState.java:44)
     *         - locked <0x00000007111929e0> (a java.lang.Class for com.zto.sxy.threads.ThreadState$Blocked)
     *         at java.lang.Thread.run(java.base@11.0.3/Thread.java:834)
     *
     * "BlockedThread-2" #19 prio=5 os_prio=0 cpu=0.00ms elapsed=30.41s tid=0x0000027582220000 nid=0xf60 waiting for monitor entry  [0x000000ea848fe000]
     *    java.lang.Thread.State: BLOCKED (on object monitor)
     *         at com.zto.sxy.threads.ThreadState$Blocked.run(ThreadState.java:44)
     *         - waiting to lock <0x00000007111929e0> (a java.lang.Class for com.zto.sxy.threads.ThreadState$Blocked)
     *         at java.lang.Thread.run(java.base@11.0.3/Thread.java:834)
     *
     * "DestroyJavaVM" #20 prio=5 os_prio=0 cpu=296.88ms elapsed=30.41s tid=0x00000275db4a7800 nid=0x3b48 waiting on condition  [0x0000000000000000]
     *    java.lang.Thread.State: RUNNABLE
     *
     * "VM Thread" os_prio=2 cpu=0.00ms elapsed=30.72s tid=0x00000275fecce000 nid=0x1108 runnable
     *
     * "GC Thread#0" os_prio=2 cpu=0.00ms elapsed=30.74s tid=0x00000275db4c0800 nid=0x2ff8 runnable
     *
     * "G1 Main Marker" os_prio=2 cpu=0.00ms elapsed=30.74s tid=0x00000275db51c800 nid=0x2108 runnable
     *
     * "G1 Conc#0" os_prio=2 cpu=0.00ms elapsed=30.74s tid=0x00000275db51d800 nid=0x18b4 runnable
     *
     * "G1 Refine#0" os_prio=2 cpu=0.00ms elapsed=30.74s tid=0x00000275febb9000 nid=0x112c runnable
     *
     * "G1 Young RemSet Sampling" os_prio=2 cpu=0.00ms elapsed=30.73s tid=0x00000275febba000 nid=0x878 runnable
     * "VM Periodic Task Thread" os_prio=2 cpu=0.00ms elapsed=30.41s tid=0x00000275ff81c800 nid=0xe00 waiting on condition
     *
     * JNI global refs: 38, weak refs: 1637
     */


}
