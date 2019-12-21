package com;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author spilledyear
 * @date 2019/12/2 12:18
 */
public class Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture future = new CompletableFuture<>();
        future.complete("罗晓帅333");
        System.out.println("我是中国人");

        Thread t = Thread.currentThread();
        new Thread(() -> {
            try {
                System.out.println(t.getName());

                System.out.println(Thread.currentThread().getName());
                Thread.sleep(5000);

                // 阻塞的线程interrupt将返回
//                t.interrupt();

                // 次线程interrupt对future不影响
//                Thread.currentThread().interrupt();

                // 手动完成
                future.complete("罗晓帅");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        System.out.println("我是中国人 " + future.get());

        Thread.sleep(Integer.MAX_VALUE);
    }
}
