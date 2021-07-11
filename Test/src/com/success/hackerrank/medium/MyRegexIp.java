package com.success.hackerrank.medium;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Write a class called MyRegex which will contain a string pattern. You need to write a regular
 * expression and assign it to the pattern such that it can be used to validate an IP address. Use
 * the following definition of an IP address:
 *
 * <p>IP address is a string in the form "A.B.C.D", where the value of A, B, C, and D may range from
 * 0 to 255. Leading zeros are allowed. The length of A, B, C, or D can't be greater than 3.
 *
 * @author Tamil
 */
public class MyRegexIp {
  // ^ start of the line
  // $ end of the line
  // () - represent a group
  // | - or

  /*
   * ([0-9]|[0-9][0-9]|[01][0-9][0-9]|2[0-4][0-9]|25[0-5])
   *  simple 0-9 (single digit) [0-9]
   *  or from 00-99 (two digits) [0-9][0-9]
   *  or from 000-199 (three digits) [01][0-9][0-9]
   *  or from 200 - 249 2[0-4][0-9]
   *  or 250 to 255 25[0-5]
   */

  /*
   * inputs
   * 249.249.249.249
   * 249.1.000.02
   * 2.49.24.257
   * 1.b.c.d
   * 0000.11.11.112
   * .11.112.11.33
   */

  private static final String PATTERN =
      "^([0-9]|[0-9][0-9]|[01][0-9][0-9]|2[0-4][0-9]|25[0-5])\\.([0-9]|[0-9][0-9]|[01][0-9][0-9]|2[0-4][0-9]|25[0-5])\\.([0-9]|[0-9][0-9]|[01][0-9][0-9]|2[0-4][0-9]|25[0-5])\\.([0-9]|[0-9][0-9]|[01][0-9][0-9]|2[0-4][0-9]|25[0-5])$";

  public static void main(String[] args) {

    Pattern pattern = Pattern.compile(PATTERN);
    Matcher matcher = pattern.matcher("249.249.249.249");
    if (matcher.find()) {
      System.out.println("valid");
    } else {
      System.out.println("invalid");
    }
  }
}
