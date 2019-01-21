package com.zto.sxy.socket;

public class Test {
    static A a2 = new A();

    public static void main(String[] args) {
        A a1 = new A();

        System.out.println(a1.i);
        System.out.println(a2.i);

//        System.out.println(findNextPrime(23));

    }


    int findNextPrime(int i) {
        if(i < 0){
            return -1;
        }

        while(true){
            i++;
            int mid = (int) Math.sqrt(i) + 1;
            for (int j = 2; j <= mid; j++) {
                if (i % j == 0) {
                    break;
                }
                if (j == mid) {
                    return i;
                }
            }
        }
    }
}

class A {
    A() {
        i = (j++ != 0) ? ++j : --j;
    }

    public int i;
    public static int j = 0;
}
