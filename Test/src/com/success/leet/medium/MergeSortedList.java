package com.success.leet.medium;
/**
 * https://leetcode.com/problems/merge-k-sorted-lists/ 
 * 
 * You are given an array of k linked-lists lists, each linked-list is sorted in ascending order.
 *
 * <p>Merge all the linked-lists into one sorted linked-list and return it.
 * 
 * Time complexity : O(kN) where k is the number of linked lists.
 * Space complexity : O(1) We can merge two sorted linked list in O(1) space.
 * 
 *
 * @author Tamil
 */
public class MergeSortedList {
  public static void main(String[] args) {

    ListNode l1 = new ListNode(1);
    ListNode l11 = new ListNode(3);
    l1.next = l11;

    l11.next = new ListNode(5);
    l11 = l11.next;

    l11.next = new ListNode(7);

    ListNode l2 = new ListNode(2);
    ListNode l22 = new ListNode(6);
    l2.next = l22;

    ListNode[] lists = new ListNode[] {l1, l2};
    new Solution().mergeKLists(lists);
  }

  public static class Solution {

    public ListNode mergeKLists(ListNode[] lists) {
      if (lists.length == 0) {
        return null;
      } else if (lists.length == 1) {
        return lists[0];
      } else {
        ListNode result = new ListNode();
        for (int i = 0; i < lists.length; i++) {
          result.next = merge(result.next, lists[i]);
        }
        return result.next;
      }
    }

    private ListNode merge(ListNode node1, ListNode node2) {

      ListNode dummy = new ListNode();
      ListNode result = dummy;
      /*
       *  three scenarios.
       *  1. both numbers are equal, then add both numbers to the result and move the pointer in both nodes
       *  2. n1 > n2, add n1 to the result and move the pointer in n1
       *  2. n1 < n2, add n2 to the result and move the pointer in n2
       */
      while (node1 != null || node2 != null) {
        int n1 = node1 != null ? node1.val : -1;
        int n2 = node2 != null ? node2.val : -1;

        if (node1 == null) {
          result.next = new ListNode(n2);
          node2 = node2.next;
        } else if (node2 == null) {
          result.next = new ListNode(n1);
          node1 = node1.next;
        } else if (n1 == n2) {
          result.next = new ListNode(n1);
          result = result.next;

          result.next = new ListNode(n2);

          node1 = node1.next;
          node2 = node2.next;

        } else if (n1 < n2) {
          result.next = new ListNode(n1);
          node1 = node1.next;
        } else {
          // n1>n2
          result.next = new ListNode(n2);
          node2 = node2.next;
        }
        result = result.next;
      }

      return dummy.next;
    }
  }
}
