package com.zto.sxy.java8.effective.optional;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author spilledyear
 * @date 2019-01-15 18:14
 */
public class OptionalTest {
    @Test
    public void test() {
        Object obj = null;

        Optional<Object> op1 = Optional.empty();
//        System.out.println(op1.get());
        System.out.println(op1);

//        Optional<Object> op2 = Optional.of(obj);
//        System.out.println(op2.get());

        Optional<Object> op3 = Optional.ofNullable(obj);
        System.out.println(op3);

        Object obj4 = Optional.ofNullable(obj).orElseGet(Object::new);
        System.out.println(obj4);


        Object obj5 = Optional.ofNullable(obj).orElse(new Object());
        System.out.println(obj5);


//        Optional.ofNullable(obj).map(Collections::singleton).orElse(Collections.emptyList());

        List list = new ArrayList();
        Optional.ofNullable(list).orElse(Collections.emptyList());

        List list2 = Optional.ofNullable(list).orElse(Collections.emptyList());
        list2.stream().filter(v -> v != null).collect(Collectors.toList());
//        System.out.println(list2);


        List<String> list4 = Arrays.asList("ffff", "gggg", "hhhh");
        Stream.of(list4).flatMap(v -> v.stream()).filter(v -> v != null);


        List[] listarray = new ArrayList[4];
//        Stream.of(listarray).

        Stream<List<Integer>> inputStream = Stream.of(
                Arrays.asList(1),
                Arrays.asList(2, 3),
                Arrays.asList(4, 5, 6)
        );

        String[] strArray = new String[]{"a", "b", "c"};
        Stream.of(strArray).filter(v -> "".equals(v));

    }


    public static final <T> List<T> emptyList() {
        return (List<T>) new ArrayList<>();
    }

}
