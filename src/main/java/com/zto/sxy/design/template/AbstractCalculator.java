package com.zto.sxy.design.template;

/**
 * Created by spilledyear on 2017/9/6.
 */
public abstract class AbstractCalculator {

    public final int calculate(String exp, String opt){
        int[] array = split(exp, opt);
        return calculate(array[0],array[1]);
    }

    public abstract int calculate(int num1, int num2);


    public int[] split(String exp, String opt){
        String[] array = exp.split("opt");
        int[] arrayInt = new int[2];
        arrayInt[0] = Integer.parseInt(array[0]);
        arrayInt[1] = Integer.parseInt(array[1]);
        return arrayInt;
    }
}
