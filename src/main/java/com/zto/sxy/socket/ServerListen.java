package com.zto.sxy.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

public class ServerListen implements Runnable {

    // 静态才能锁住，为啥？要不然就是每个对象自己的对象，还锁毛线啊
    private static int count;

    private static ReentrantLock lock = new ReentrantLock();

    private Socket socket;


    public ServerListen(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        increaseCount();
        int res = getCount();

        Scanner scanner = new Scanner(System.in);
        try {
            OutputStreamWriter write = new OutputStreamWriter(socket.getOutputStream());
            BufferedReader reder = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            PrintThread printThread = new PrintThread(reder, "CLIENT");
            new Thread(printThread).start();

            String send = "";
            while (!"bye".equals(send)) {
                send = scanner.nextLine();
                write.write(send + "\r");
                write.flush();
            }

            printThread.setStop(true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("服务端关闭连接");
                socket.close();
                scanner.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    int getCount() {
        int result = 0;
        try {
            lock.lock();
            result = count;
        } finally {
            lock.unlock();
        }
        return result;
    }

    void increaseCount() {
        try {
            lock.lock();
            count++;
        } finally {
            lock.unlock();
        }
    }
}
