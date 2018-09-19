package com.hand.sxy.zk;

public class Test2 {
    public static void main(String[] args) {
        DistributedLock distributedLock = DistributedLock.getInstance();

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                try {
                    distributedLock.tryLock();
                    System.out.println("TEST2 " + Thread.currentThread().getName() + " 获得锁开始处理业务 " + System.currentTimeMillis());
                    Thread.sleep(1000);
                    distributedLock.releaseLock();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
