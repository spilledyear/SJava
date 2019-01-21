package com.zto.sxy.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

    private ServerSocket serverSocket;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(100);
            while (true) {
                Socket socket = serverSocket.accept();
                executorService.submit(new ServerListen(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
