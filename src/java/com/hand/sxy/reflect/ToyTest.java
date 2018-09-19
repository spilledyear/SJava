package com.hand.sxy.reflect;

/**
 * Created by spilledyear on 2017/8/13.
 */
public class ToyTest {
    public static void main(String[] args) throws Exception {
        Class c = null;
        c = Class.forName("com.sxy.reflect.FancyToy");
        printInfo(c);

        for (Class face : c.getInterfaces())
            printInfo(face);

        Class up = c.getSuperclass();
        Object obj = up.newInstance();
        printInfo(obj.getClass());
    }

    static void printInfo(Class cc) {
        System.out.println("className " + cc.getName() + " is interface? [ " + cc.isInterface() + " ]");
        System.out.println("simpleName " + cc.getSimpleName());
        System.out.println("CanonicalName " + cc.getCanonicalName());
    }
}

interface HasBatteries {
}

interface Waterproof {
}

interface Shoots {
}

class Toy {
    Toy() {
    }

    Toy(int i) {
    }
}

class FancyToy extends Toy implements HasBatteries, Waterproof, Shoots {
    FancyToy() {
        super(1);
    }
}


