package com.success.hackerrank.medium;

import java.util.Set;
import java.util.TreeSet;

public class StringDivisibility {
  private static int divide(String s, String t) {

    // skip the negative cases
    if (t.length() > s.length() || !s.startsWith(t) || s.length() % t.length() != 0) {
      return -1;
    }

    // work on t, find the smallest sub by dividing the t by its divisors...
    Set<Integer> divisors = new TreeSet<>();
    // by default 1 and the number itself become its divisors
    divisors.add(t.length());
    divisors.add(1);

    for (int i = 2; i <= Math.sqrt(t.length()); i++) {
      if (t.length() % i == 0) {
        divisors.add(i);
        divisors.add(t.length() / 2);
      }
    }

    for (int divisor : divisors) {
      // for each divisor starting from small, substring the t, and concat and match against t
      String concatenatedString = repeat(t.substring(0, divisor), t.length() / divisor);
      if (concatenatedString.equals(t)) {
        return divisor;
      }
    }
    return 0;
  }

  private static String repeat(String s, int times) {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < times; i++) {
      result.append(s);
    }
    return result.toString();
  }

  public static void main(String[] args) {
    System.out.println(divide("bcdbcdbcdbcd", "bcdbcdbcdbcd"));
  }
}
