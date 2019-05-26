package com.zto.sxy.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PrioritySetting {

    private static volatile boolean startFlag = true;

    private static volatile boolean endFlag = true;

    public static void main(String[] args) throws InterruptedException {
        List<Task> tasks = new ArrayList<>(20);
        for (int i = 0; i < 10; i++) {
            int priority = i < 5 ? Thread.MIN_PRIORITY : Thread.MAX_PRIORITY;
            Task task = new Task(priority);
            tasks.add(task);
            Thread thread = new Thread(task, "thread:" + i);
            thread.setPriority(priority);
            thread.start();
        }


        startFlag = false;
        TimeUnit.SECONDS.sleep(10);
        endFlag = false;

        for (Task task : tasks) {
            System.out.println("priority: " + task.priority + "  ;   count: " + task.count);
        }
    }


    static class Task implements Runnable {
        private int priority;

        private long count;

        public Task(int priority) {
            this.priority = priority;
        }


        @Override
        public void run() {
            while (startFlag) {
                Thread.yield();
            }


            while (endFlag) {
                Thread.yield();
                count++;
            }
        }
    }


}
