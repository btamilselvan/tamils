package com.success.java8;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;


public class DateUtils {
  public static void main(String[] args) {
    System.out.println("test");
    System.out.println(new Date());
    System.out.println(Instant.now());
    long l = 330;
    System.out.println(Instant.now().plus(l, ChronoUnit.MINUTES));
    
//    13:00
  }
}
