package com.hand.sxy.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;

/**
 * User: Administrator
 * Date: 14-1-29
 * Time: 下午7:40
 */
public class Server2 extends ServerSocket {

    private FileOutputStream serverLogs = new FileOutputStream("D:/connect.log", true);
    private static final int PORT = 18888; //指定server端 端口号
    private static LinkedList<String> messageHandler = new LinkedList<>(); //存放消息的队列
    private static ArrayList<Client> threads = new ArrayList<>();//存放client线程
    private static ArrayList<String> nikeNameList = new ArrayList<>();//存放nikeName
    private static boolean isClean = true;

    public Server2() throws Exception {
        super(PORT);
        //启动消息管理线程
        new MessageHandler();
        //记录服务端启动日志
        Calendar calendar = Calendar.getInstance();
        byte[] logs = (calendar.getTime().toString() + "server is start.").getBytes();
        serverLogs.write(logs);
        //响应client连接请求，把socket对象放客户端线程
        try {
            while (true) {
                Socket socket = accept();
                new Client(socket);
            }
        } finally {
            close();
        }
    }

    public static void main(String[] args) throws Exception {
        new Server2();
    }

    class MessageHandler extends Thread {

        public MessageHandler() {
            start();
        }

        public void run() {
            while (true) {
                if (!isClean) {
                    String message = messageHandler.getLast();
                    for (Client client : threads) {
                        client.pushMessages(message);
                    }
                    isClean = true;
                }
                //  isClean = true; 这句不能写在这里,想想为什么？


            }
        }
    }

    class Client extends Thread {
        private BufferedReader in;
        private PrintWriter out;
        private String nikeName;
        private String input;
        private Socket clientSocket;

        public Client(Socket client) throws Exception {
            this.clientSocket = client;
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println("------------welcome-------------");
            out.println("please input your nikeName:");
            start();
        }

        @Override
        public void run() {
            try {
                input = in.readLine();
                nikeName = input;
                int count = 0;
                while (!"bye".equals(input)) {
                    if (count++ == 0) {
                        out.println("ok,welcome<" + nikeName + ">,you can talk now.");
                        nikeNameList.add(nikeName);
                        threads.add(this);
                        messageHandler.addLast("<" + nikeName + "> entered the chartRoom.");
                    } else {
                        if ("L".equals(input)) {
                            showAllUsers();
                        } else {
                            pullMessages("<" + nikeName + ">:" + input);
                        }

                    }
                    input = in.readLine();
                }
                out.println("bye <" + nikeName + ">");
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    //pullMessages("<"+nikeName+"> is left the room."); 这句不能写在这里，呵呵，想想为什么？
                    nikeNameList.remove(nikeName);
                    threads.remove(this);
                    in.close();
                    out.close();
                    if (!clientSocket.isClosed()) {
                        clientSocket.close();
                    }
                    pullMessages("<" + nikeName + "> is left the room."); //应该写在这里
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //向当前线程展示所有在线用户
        public void showAllUsers() {
            out.println("------show all users-------");
            for (String name : nikeNameList) {
                out.println("<" + name + "> is online.");
            }
            out.println("---------------------------");
        }

        //把当前线程的消息放入消息队列
        public void pullMessages(String message) {
            messageHandler.addLast(message);
            isClean = false;
        }

        //推送消息
        public void pushMessages(String message) {
            out.println(message);
        }
    }
}
