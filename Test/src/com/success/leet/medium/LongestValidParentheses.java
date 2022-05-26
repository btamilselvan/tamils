package com.success.leet.medium;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * https://leetcode.com/problems/longest-valid-parentheses/submissions/ Core idea: maintain
 * unbalanced parentheses position and find the distance between the unbalanced parentheses.. add
 * logic to handle if the pointer position is at the beginning or end of the string
 *
 * @author Tamil
 */
public class LongestValidParentheses {

  public static void main(String[] args) {

    System.out.println("enter input");
    try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
      String line = null;
      while ((line = br.readLine()) != null && line.length() > 0) {
        //        findLongestValidParantheses(line);
        findLongestValidParanthesesV2(line);
      }
      System.out.println("done");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static int findLongestValidParanthesesV2(String s) {

    if (s.length() < 2) {
      return 0;
    }

    // use stack to balance the parentheses, (could do without stack too..)
    Stack<Character> stack = new Stack<>();

    char[] arr = s.toCharArray();
    // maintain unbalanced parentheses position
    List<Integer> unbalancedPointers = new ArrayList<>();

    for (int i = 0; i < arr.length; i++) {
      char item = arr[i];

      if (item == ')' && !stack.isEmpty() && stack.peek() == '(') {
        // a balanced parentheses.. remove the pair and remove '(' from unbalanced pointer
        stack.pop();
        unbalancedPointers.remove(unbalancedPointers.size() - 1);
      } else {
        stack.push(item);
        unbalancedPointers.add(i);
      }

      /*if (item == ')') {
        if (!stack.isEmpty() && stack.peek() == '(') {
          // a balanced the parentheses.. remove the pair and remove '(' from unbalanced pointer
          stack.pop();
          unbalancedPointers.remove(unbalancedPointers.size() - 1);
        } else {
          unbalancedPointers.add(i); // unbalanced
        }
      } else if (item == '(') {
        stack.push(item);
        unbalancedPointers.add(i);
      }*/
    }
    System.out.println(unbalancedPointers);
    int length = findFinalLength(unbalancedPointers, s);
    // due to the nature of our length calculation, an odd number could be returned as length. but
    // the length cannot be an odd number (balanced pairs). so fix the length here
    length = length % 2 == 0 ? length : length - 1;
    System.out.println(length);
    return length;
  }

  private static int findFinalLength(List<Integer> unbalancedPointers, String line) {
    if (unbalancedPointers.isEmpty()) {
      // a balanced string
      return line.length();
    }
    if (unbalancedPointers.size() == line.length()) {
      // no balanced parentheses found
      return 0;
    }
    if (unbalancedPointers.size() == 1) {
      // contains one unbalanced parentheses
      int pos = unbalancedPointers.get(0);
      if (pos == 0 || pos == line.length() - 1) {
        // the unbalanced parentheses is either beginning or end character in the line
        return line.length() - 1;
      } else {
        // somewhere in the middle. find the difference b/w beginning to the pointer pos and pointer
        // pos
        // to the end and return the longest
        return line.length() - pos > pos ? line.length() - pos : pos;
      }
    }
    // contains unbalanced parentheses in multiple locations. find the length difference between
    // adjacent locations
    int prevLength = 0;
    int curLength = 0;
    prevLength = unbalancedPointers.get(0);
    for (int i = 1; i < unbalancedPointers.size(); i++) {
      curLength = unbalancedPointers.get(i) - unbalancedPointers.get(i - 1);
      if (curLength > prevLength) {
        prevLength = curLength;
      }
    }
    // identify the distance between last unbalanced parentheses and the rest of the string
    int lengthBetweenLastUnbalancedPointerAndLastChar =
        line.length() - unbalancedPointers.get(unbalancedPointers.size() - 1);
    if (lengthBetweenLastUnbalancedPointerAndLastChar > prevLength) {
      prevLength = lengthBetweenLastUnbalancedPointerAndLastChar;
    }
    return prevLength;
  }
}
