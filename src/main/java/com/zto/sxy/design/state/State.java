package com.zto.sxy.design.state;

/**
 * Created by spilledyear on 2017/9/11.
 */
public class State {

    public String value;

    public void method1(){
        System.out.println("exx the first opt");
    }

    public void method2(){
        System.out.println("exe the second opt");
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
