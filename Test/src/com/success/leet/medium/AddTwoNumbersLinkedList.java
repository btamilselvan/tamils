package com.success.leet.medium;
/**
 * https://leetcode.com/problems/add-two-numbers/ You are given two non-empty linked lists
 * representing two non-negative integers. The digits are stored in reverse order, and each of their
 * nodes contains a single digit. Add the two numbers and return the sum as a linked list.
 *
 * <p>You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 *
 * @author Tamil
 */
public class AddTwoNumbersLinkedList {
  public static void main(String[] args) {

    ListNode l1 = new ListNode(9);
    ListNode l11 = new ListNode(9);
    l1.next = l11;

    l11.next = new ListNode(2);
    l11 = l11.next;

    l11.next = new ListNode(1);

    ListNode l2 = new ListNode(9);
    ListNode l22 = new ListNode(9);
    l2.next = l22;

    //    l22.next = new ListNode(2);
    //    l22 = l22.next;

    //    l22.next = new ListNode(1);

    new Solution().addTwoNumbers(l1, l2);
  }

  public static class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

      ListNode rn = new ListNode();
      ListNode rn1 = rn;

      int sum = l1.val + l2.val;
      int reminder = sum / 10;
      rn.val = sum % 10;

      l1 = l1.next;
      l2 = l2.next;

      while (l1 != null || l2 != null) {

        int n1 = l1 != null ? l1.val : 0;
        int n2 = l2 != null ? l2.val : 0;
        l1 = l1 != null ? l1.next : null;
        l2 = l2 != null ? l2.next : null;

        sum = n1 + n2 + (reminder > 0 ? reminder : 0);
        reminder = sum / 10;

        rn1.next = new ListNode(sum % 10);
        rn1 = rn1.next;
      }

      if (reminder > 0) {
        rn1.next = new ListNode(1);
      }

      return rn;
    }

  }
}
