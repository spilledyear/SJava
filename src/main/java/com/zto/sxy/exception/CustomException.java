package com.zto.sxy.exception;

/**
 * Created by spilledyear on 2017/8/29.
 */
public class CustomException extends Exception {
    public CustomException(){
        super();
    }

    public CustomException(String error){
        super(error);
    }
}
