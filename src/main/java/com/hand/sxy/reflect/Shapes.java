package com.hand.sxy.reflect;

import java.util.Arrays;
import java.util.List;

/**
 * Created by spilledyear on 2017/8/12.
 */
public class Shapes {
    public static void main(String[] args) {
        List<Shape> shapeList = Arrays.asList(new Circel(), new Square(), new Triangle());
        for (Shape shape : shapeList) {
            shape.draw();
        }
    }
}

abstract class Shape {
    void draw() {
        System.out.println(this + "draw()");
    }

    abstract public String toString();
}

class Circel extends Shape {

    @Override
    public String toString() {
        return "Circle";
    }
}

class Square extends Shape {

    @Override
    public String toString() {
        return "Square";
    }
}

class Triangle extends Shape {
    @Override
    public String toString() {
        return "Triangle";
    }
}

