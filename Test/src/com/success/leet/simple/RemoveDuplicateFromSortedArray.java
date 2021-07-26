package com.success.leet.simple;
/**
 * https://leetcode.com/problems/remove-duplicates-from-sorted-array
 *
 * <p>Given an integer array nums sorted in non-decreasing order, remove the duplicates in-place
 * such that each unique element appears only once. The relative order of the elements should be
 * kept the same.
 *
 * <p>Since it is impossible to change the length of the array in some languages, you must instead
 * have the result be placed in the first part of the array nums. More formally, if there are k
 * elements after removing the duplicates, then the first k elements of nums should hold the final
 * result. It does not matter what you leave beyond the first k elements.
 *
 * <p>Return k after placing the final result in the first k slots of nums.
 *
 * <p>Do not allocate extra space for another array. You must do this by modifying the input array
 * in-place with O(1) extra memory.
 *
 * <p>Complexity analysis
 *
 * <p>Time complextiy : O(n). Assume that n is the length of array. Each of i and j traverses at
 * most n steps.
 *
 * <p>Space complexity : O(1).
 *
 * @author Tamil
 */
public class RemoveDuplicateFromSortedArray {
  public static void main(String[] args) {
    System.out.println(removeDuplicates(new int[] {1, 1, 1, 2}));
  }

  static int removeDuplicates(int[] nums) {

    if (nums.length == 0) {
      return 0;
    }

    int j = 0; // holds the final unique array index

    for (int i = 0; i < nums.length - 1; i++) {
      if (nums[i] != nums[i + 1]) {
        // not duplicate
        nums[j++] = nums[i]; // set the value starting from index 0..
      }
    }
    nums[j++] = nums[nums.length - 1]; // always consider the last element
    for (int i = 0; i < nums.length; i++) {
      System.out.println(nums[i]);
    }
    System.out.println("length is " + j);
    return j;
  }
}
