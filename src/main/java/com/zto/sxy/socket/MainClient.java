package com.zto.sxy.socket;

public class MainClient {

    public static void main(String[] args) throws Exception{
        new Thread(new ClientThread(5555)).start();

//        ExecutorService executorService = Executors.newFixedThreadPool(100);
//        for(int i = 0; i< 10;i++){
//            Thread.sleep(500);
//            executorService.submit(new ClientThread(5555));
//        }
    }
}
