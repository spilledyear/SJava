package com.zto.sxy.design.interpreter;

/**
 * Created by spilledyear on 2017/9/11.
 */
public class Context {

    private int number1;
    private int number2;

    public Context(int number1, int number2){
        this.number1 = number1;
        this.number2 = number2;
    }

    public int getNumber1() {
        return number1;
    }

    public void setNumber1(int number1) {
        this.number1 = number1;
    }

    public int getNumber2() {
        return number2;
    }

    public void setNumber2(int number2) {
        this.number2 = number2;
    }
}
