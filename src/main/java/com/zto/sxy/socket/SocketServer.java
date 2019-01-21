package com.zto.sxy.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by spilledyear on 2017/9/11.
 */
public class SocketServer {
    public static void main(String[] args) throws Exception {

        ServerSocket server = new ServerSocket(9989);
        while(true){
            Socket socket = server.accept();
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = br.readLine();
            System.out.println("your input is "+ line);
            br.close();
            socket.close();
            server.close();
        }

    }
}
