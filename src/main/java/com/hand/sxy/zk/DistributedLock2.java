package com.hand.sxy.zk;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.RetryNTimes;

public class DistributedLock2 {
    public final static String zkServerPath = "192.168.11.223:2181";

    private final static String LOCK_PROJECT = "/lock";
    private final static String LOCK_NODE = "/lockTest";

    public static void main(String[] args) throws Exception {
        RetryPolicy retryPolicy = new RetryNTimes(3, 5000);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(zkServerPath)
                .sessionTimeoutMs(10000).retryPolicy(retryPolicy)
                .namespace("workspace").build();
        client.start();

        //创建分布式锁, 锁空间的根节点路径为/workspace/lock/lockTest
        InterProcessMutex mutex = new InterProcessMutex(client, LOCK_PROJECT + LOCK_NODE);

        mutex.acquire();

        //获得了锁, 进行业务流程
        System.out.println("Enter mutex");

        //完成业务流程, 释放锁
        mutex.release();

        //关闭客户端
        client.close();
    }
}
