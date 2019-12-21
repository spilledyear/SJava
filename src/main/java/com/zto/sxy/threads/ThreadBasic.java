package com.zto.sxy.threads;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.function.Consumer;

public class ThreadBasic {
    private static int result = 0;

    private static volatile boolean finished = false;

    public static void main(String[] args) throws InterruptedException {
        test01();
    }


    /**
     * 对于新线程的执行结果，并不需要立即知道，在访问全部变量的时候，只需要获取它的值就好了。比如通过定时任务去更新缓存：
     * 不需要关注任务什么时候执行，我需要的只是缓存的值，任务执行了我就获取最新的值，没有执行我就获取旧的值。
     * <p>
     * 但这实际上并不算是返回值，只能算是一种值的传递吧
     */
    private static void test01() throws InterruptedException {
        new Thread(() -> {
            System.out.println("处理业务逻辑");
            result = 1000;
        }).start();

        Thread.sleep(1000);
        System.out.println(result);
    }

    /**
     * 这有什么缺点？什么时候返回并不知道，你可能需要自己通过一个标识变量+轮询的方式去获取结果，
     */
    private static void test02() {
        new Thread(() -> {
            System.out.println("处理业务逻辑");
            finished = true;
        }).start();

        while (!finished) {
        }
    }


    private static void test03() {
        MyRunnable<String> myRunnable = new MyRunnable(() -> {
            // 模拟耗时的业务操作
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "我是结果";
        });
        System.out.println("开始时间 " + LocalDateTime.now());
        new Thread(myRunnable).start();

//        System.out.println("result: " + myRunnable.get());
//        System.out.println("结束时间 " + LocalDateTime.now());

        myRunnable.addListener(result -> {
            System.out.println("当xxx执行完成之后，线程：" + Thread.currentThread().getName() + " 执行一些其他的任务");
            result = result + "   ggggg";
            System.out.println(result);
        });

        // 那如果想加两个怎么办？ 继续加
        myRunnable.addListener(result -> {
            System.out.println("当xxx执行完成之后，线程：" + Thread.currentThread().getName() + " 执行一些其他的任务");
            result = result + "   vvvvvvv";
            System.out.println(result);
        });
        System.out.println("线程：" + Thread.currentThread().getName() + " 继续干一些其他的事情");
    }

    private static void test04() {
        CallableThread callableThread = new CallableThread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "ccccc";
        });
        callableThread.start();
        System.out.println("开始时间 " + LocalDateTime.now());
        System.out.println(callableThread.get());
        System.out.println("结束时间 " + LocalDateTime.now());
    }

    private static void testFutureTask() throws ExecutionException, InterruptedException {
        FutureTask<String> future = new FutureTask<>(() -> {
            Thread.sleep(3000);
            System.out.println(System.currentTimeMillis());
            return "hehehh";
        });
        new Thread(future).start();
        System.out.println("Start Get Result : " + System.currentTimeMillis());
        System.out.println("Get Result : " + future.get() + System.currentTimeMillis());
    }
}


/**
 * 任务的逻辑写到哪里？
 * 可以定义一个@FunctionalInterface接口，并且有返回值。 Task
 * <p>
 * 这样貌似也可以，但是不太好。Thread本来只是用于处理和线程相关的事情，现在将它和逻辑(Task)绑定在一起，如果有多个任务想共用一个Thread，那返回值怎么处理？
 */
class CallableThread<T> extends Thread {
    private Task<T> task;

    private T result;

    private volatile boolean finished = false;

    public CallableThread(Task<T> task) {
        this.task = task;
    }

    @Override
    public void run() {
        synchronized (this) {
            result = task.call();
            finished = true;
            notifyAll();
        }
    }

    public T get() {
        synchronized (this) {
            while (!finished) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }
    }
}


class MyRunnable<T> implements Runnable {
    private Task<T> task;

    private T result;

    private volatile boolean finished = false;

    public MyRunnable(Task<T> task) {
        this.task = task;
    }

    @Override
    public void run() {
        synchronized (this) {
            result = task.call();
            finished = true;
            notifyAll();
        }
    }

    public T get() {
        synchronized (this) {
            while (!finished) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }
    }


    public MyRunnable addListener(Consumer c) {
        // 这里一般用线程池
        new Thread(() -> {
            while (!finished) {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            c.accept(result);
        }).start();
        return this;
    }
}


/**
 * 逻辑写到这里
 *
 * @param <T>
 */
@FunctionalInterface
interface Task<T> {
    T call();
}



