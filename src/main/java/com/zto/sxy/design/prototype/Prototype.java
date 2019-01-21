package com.zto.sxy.design.prototype;

import java.io.*;

/**
 * Created by spilledyear on 2017/9/6.
 *
 * 原型模式
 * 该模式的思想就是将一个对象作为原型，对其进行复制、克隆，产生一个和原对象类似的新对象
 */
public class Prototype implements Cloneable, Serializable{

    public static final long serialVersionId = 1L;
    private String str;
    private Serializable obj;


    //浅复制
    public Object clone() throws CloneNotSupportedException{
        Prototype prototype = (Prototype) super.clone();
        return prototype;
    }


    //深复制
    public Object deepClone() throws IOException,ClassNotFoundException{

        //写入当前对象的二进制流
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(this);

        //写出二进制流产出的对象
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return ois.readObject();
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public Serializable getObj() {
        return obj;
    }

    public void setObj(Serializable obj) {
        this.obj = obj;
    }
}
