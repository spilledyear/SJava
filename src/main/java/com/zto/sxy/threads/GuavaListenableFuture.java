package com.zto.sxy.threads;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.google.common.util.concurrent.MoreExecutors;

import java.time.LocalDateTime;

public class GuavaListenableFuture {
    public static void main(String[] args) {
        testCallback();
    }

    private static void testCallback() {
        ListenableFutureTask futureTask = ListenableFutureTask.create(() -> {
            System.out.println("执行任务开始  " + LocalDateTime.now());
            Thread.sleep(3000);
            System.out.println("执行任务完成  " + LocalDateTime.now());
            return "结果";
        });

        Futures.addCallback(futureTask, new FutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("执行成功: " + result);
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("执行失败");
            }
        }, MoreExecutors.directExecutor());

        new Thread(futureTask).start();
    }


    private static void testListener() {
        ListenableFutureTask futureTask = ListenableFutureTask.create(() -> {
            System.out.println("执行任务开始  " + LocalDateTime.now());
            Thread.sleep(3000);
            System.out.println("执行任务完成  " + LocalDateTime.now());
            return "结果";
        });


        futureTask.addListener(() -> System.out.println("获取结果之后，输出一条日志"), MoreExecutors.directExecutor());
        new Thread(futureTask).start();
    }
}
