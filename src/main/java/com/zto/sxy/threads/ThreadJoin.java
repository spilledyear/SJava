package com.zto.sxy.threads;

public class ThreadJoin {
    public static void main(String[] args) {
        Thread previous = Thread.currentThread();

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Runner(previous), "Thread:" + i);
            thread.start();

            previous = thread;
        }

        SleepUtils.second(3);
        System.out.println(Thread.currentThread().getName() + " terminate");
    }


    static class Runner implements Runnable {
        private Thread thread;

        public Runner(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + " terminate");
        }
    }
}
