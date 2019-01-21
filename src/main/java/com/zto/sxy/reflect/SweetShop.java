package com.zto.sxy.reflect;

/**
 * Created by spilledyear on 2017/8/13.
 */
public class SweetShop {
    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println("inside main");
        new Candy();
        System.out.println(" after create Candy");

//        Class.forName("Gum");   //会抛异常
        Class.forName("com.sxy.reflect.Gum");
        System.out.println("after create Gum");

        new Cookie();
        System.out.println("after create Cookie");
    }
}

class Candy {
    static {
        System.out.println(" loading Candy");
    }
}

class Cookie {
    static {
        System.out.println(" loading Cookie");
    }
}

class Gum {
    static {
        System.out.println(" loading Gum");
    }
}


