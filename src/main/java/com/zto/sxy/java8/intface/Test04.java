package com.zto.sxy.java8.intface;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Test04 {
    public static void main(String[] args) {
        LocalDate d1 = LocalDate.now();
        System.out.println(d1.toString());

        LocalTime d2 = LocalTime.now();
        System.out.println(d2.toString());

        LocalDateTime d3 = LocalDateTime.now();
        System.out.println(d3.toString());

        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        d3 = LocalDateTime.parse("2018-09-19 11:23:22", f);
        System.out.println(d3.toString());

        ZonedDateTime zd = ZonedDateTime.now();
        zd.format(f);
        System.out.println(zd.toString());
    }
}
