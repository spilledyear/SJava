package com.zto.sxy.zk;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;

import java.util.concurrent.CountDownLatch;

public class DistributedLock {
    public final static String zkServerPath = "192.168.11.223:2181";

    private final static String LOCK_PROJECT = "/lock";

    private final static String LOCK_NODE = "/lockTest";

    private static DistributedLock distributedLock = null;

    private CuratorFramework client = null;

    private CountDownLatch zkLockPath = new CountDownLatch(1);

    private DistributedLock() {
        createClient();
        initNode();
    }

    public static DistributedLock getInstance() {
        if (distributedLock == null) {
            synchronized (DistributedLock.class) {
                if (distributedLock == null) {
                    distributedLock = new DistributedLock();
                }
            }
        }
        return distributedLock;
    }


    private void createClient() {
        RetryPolicy retryPolicy = new RetryNTimes(3, 5000);
        client = CuratorFrameworkFactory.builder()
                .connectString(zkServerPath)
                .sessionTimeoutMs(10000).retryPolicy(retryPolicy)
                .namespace("workspace").build();
        client.start();
    }

    private void initNode() {
        try {
            if (client.checkExists().forPath(LOCK_PROJECT) == null) {
                client.create()
                        .creatingParentsIfNeeded()
                        .withMode(CreateMode.EPHEMERAL)
                        .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                        .forPath(LOCK_PROJECT);
            }

            addWatcherLock(LOCK_PROJECT);

        } catch (Exception e) {
            System.out.println("连接ZK失败");
            e.printStackTrace();
        }
    }

    /**
     * 获取锁
     */
    public void tryLock() {
        while (true) {
            try {
                client.create()
                        .creatingParentsIfNeeded()
                        .withMode(CreateMode.EPHEMERAL)
                        .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                        .forPath(LOCK_PROJECT + LOCK_NODE);
                return;
            } catch (Exception e) {
                System.out.println("获取锁失败，阻塞线程");
                try {
                    if (zkLockPath.getCount() <= 0) {
                        zkLockPath = new CountDownLatch(1);
                    }
                    zkLockPath.await();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 监听
     *
     * @param path
     * @throws Exception
     */
    public void addWatcherLock(String path) throws Exception {
        final PathChildrenCache cache = new PathChildrenCache(client, path, true);
        cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                if (PathChildrenCacheEvent.Type.CHILD_REMOVED.equals(event.getType())) {
                    String path = event.getData().getPath();
                    if (path.contains(LOCK_NODE)) {
                        zkLockPath.countDown();
                    }
                }
            }
        });
    }

    /**
     * 释放锁
     *
     * @return
     */
    public boolean releaseLock() {
        try {
            if (client.checkExists().forPath(LOCK_PROJECT + LOCK_NODE) != null) {
                client.delete().forPath(LOCK_PROJECT + LOCK_NODE);
            }
        } catch (Exception e) {
            System.out.println("释放锁失败");
            return false;
        }
        return true;
    }
}
