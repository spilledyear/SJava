package com.zto.sxy.design.facade;

/**
 * Created by spilledyear on 2017/9/6.
 */
public class Computer {
    private Cpu cpu;
    private Memory memory;
    private Disk disk;

    public void startup(){
        System.out.println("start computer");
        cpu.startup();
        memory.startup();
        disk.startup();
        System.out.println("start computer finished");

    }

    public void shutdown(){
        System.out.println("shutdown computer");
        cpu.shutdown();
        memory.shutdown();
        disk.shutdown();
        System.out.println("shutdown computer finished");
    }

}
