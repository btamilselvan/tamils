package com.success.leet.medium;

import java.util.HashSet;
import java.util.Set;

/**
 * https://leetcode.com/problems/first-missing-positive/
 *
 * <p>set contains time complexity o(1)
 *
 * @author Tamil
 */
public class MyFirstMissingPositive {

  public int firstMissingPositive(int[] nums) {
    Set<Integer> set = new HashSet<>(nums.length);
    int missingElement = 1;

    for (int i = 0; i < nums.length; i++) {
      int num = nums[i];
      if (num > 0 && num <= nums.length) {
        set.add(num);
      }
    }
    for (int j = 1; j <= set.size(); j++) {
      if (set.contains(j)) {
        missingElement++;
      } else {
        break;
      }
    }
    return missingElement;
  }

  public static void main(String[] args) {
    int missingElement = new MyFirstMissingPositive().firstMissingPositive(new int[] {3, 4, 2, 1});
    System.out.println(missingElement);

    missingElement = new MyFirstMissingPositive().firstMissingPositive(new int[] {-1, 0, 21, 1});
    System.out.println(missingElement);
  }
}
