package com.zto.sxy.socket;

import java.io.*;
import java.net.Socket;

/**
 * Created by spilledyear on 2017/9/11.
 */
public class SocketClient {
    public static void main(String[] args) throws Exception{

        Socket client = new Socket("127.0.0.1", 9989);
        BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));

        PrintWriter pw = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));

        BufferedReader line = new BufferedReader(new InputStreamReader(System.in));

        pw.print(line.readLine());


        pw.close();
        line.close();
        br.close();
        client.close();

    }
}
