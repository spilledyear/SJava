package com.zto.sxy.socket;

public class MainServer {

    public static void main(String[] args) throws Exception{
        new Thread(new Server(5555)).start();

//        ExecutorService executorService = Executors.newFixedThreadPool(100);
//        for(int i = 0; i< 10;i++){
//            Thread.sleep(500);
//            executorService.submit(new ClientThread(5555));
//        }
    }
}
