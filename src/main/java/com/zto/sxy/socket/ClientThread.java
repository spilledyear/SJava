package com.zto.sxy.socket;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread implements Runnable{
    private Socket client;

    public ClientThread(int port){
        try{
            client  = new Socket("127.0.0.1", port);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try{
            Scanner scanner = new Scanner(System.in);
            BufferedReader reder = new BufferedReader(new InputStreamReader(client.getInputStream()));
            OutputStreamWriter out = new OutputStreamWriter(client.getOutputStream());

            PrintThread printThread = new PrintThread(reder, "SERVER");
            new Thread(printThread).start();

            String message = scanner.next();
            while(true){
                out.write(message + "\r");
                out.flush();

                if("bye".equals(message)){
                    break;
                }
                message = scanner.next();
            }

            printThread.setStop(true);
            client.close();
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
