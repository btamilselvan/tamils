package com.success.hackerrank.medium;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;

public class StackBalancing {

  public static void main(String[] args) {
    System.out.println("enter input");
    try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
      String line = null;

      while ((line = br.readLine()) != null && line.length() > 0) {
        balance(line);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void balance(String line) {
    Stack<Character> stack = new Stack<>();
    // {{}}({}){)
    // {{)}
    for (char c : line.toCharArray()) {
      if (c == '{' || c == '(' || c == '[') {
        stack.push(c);
      } else if (c == '}' || c == ')' || c == ']') {
        if (stack.isEmpty()) {
          System.out.println("false");
          return;
        }
        if ((c == '}' && stack.peek() == '{')
            || (c == ')' && stack.peek() == '(')
            || (c == ']' && stack.peek() == '[')) {
          stack.pop();
        }
      }
    }
    if (stack.isEmpty()) {
      System.out.println("true");
    } else {
      System.out.println("false");
    }
  }
}
