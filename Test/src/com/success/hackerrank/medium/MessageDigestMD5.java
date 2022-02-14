package com.success.hackerrank.medium;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import javax.xml.bind.DatatypeConverter;

public class MessageDigestMD5 {

  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);
    String input = scanner.next();

    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(input.getBytes());
      System.out.println(DatatypeConverter.printHexBinary(md.digest()));

      MessageDigest md1 = MessageDigest.getInstance("MD5");
      byte[] arr = md1.digest(input.getBytes());
      BigInteger bi = new BigInteger(1, arr);
      String hex = bi.toString(16);
      while (hex.length() < 32) {
        hex = "0" + hex;
      }
      System.out.println(hex);

    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    scanner.close();
  }
}
