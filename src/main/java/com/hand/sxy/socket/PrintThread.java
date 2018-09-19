package com.hand.sxy.socket;

import java.io.BufferedReader;

public class PrintThread implements Runnable {
    private BufferedReader reder;

    private boolean stop;

    private String gener;

    public PrintThread(BufferedReader reder, String gener) {
        this.reder = reder;
        this.gener = gener;
    }


    @Override
    public void run() {
        try {
            while (!stop) {
                String message = reder.readLine();
                System.out.println(gener + "：" + message);
                if ("bye".equals(message)) {
                    break;
                }

            }
        } catch (Exception e) {
            System.out.println("读取信息异常");
        }
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
}
