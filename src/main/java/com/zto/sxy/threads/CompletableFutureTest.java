package com.zto.sxy.threads;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CompletableFutureTest {
    public static void main(String[] args) throws Exception {
//        runAsync();
        allOf();
    }

    private static void runAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("任务执行所在线程 " + Thread.currentThread().getName());
            return "Hello";
        });

        System.out.println("获取值线程 " + Thread.currentThread().getName() + future.get());
    }


    private static void allOf() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "one ");
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "two ");
        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> "three ");

        CompletableFuture future = CompletableFuture.allOf(future1, future2, future3);
        future.get();

        System.out.println(future1.get());
        System.out.println(future2.get());
        System.out.println(future3.get());


        String combined = Stream.of(future1, future2, future3)
                .map(CompletableFuture::join)
                .collect(Collectors.joining(" "));
        System.out.println(combined);
    }
}
