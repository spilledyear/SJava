package com.hand.sxy.design.memento;

/**
 * Created by spilledyear on 2017/9/11.
 */
public class Original {
    private String value;

    public String getValue(){
        return this.value;
    }

    public void setValue(String value){
        this.value = value;
    }

    public Original(String value){
        this.value = value;
    }

    public Memento createMemento(){
        return new Memento(value);
    }

    public void restoreMemento(Memento memento){
        this.value = memento.getValue();
    }


}
