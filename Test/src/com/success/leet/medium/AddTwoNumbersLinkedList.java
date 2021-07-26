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

    ListNode1 l1 = new ListNode1(9);
    ListNode1 l11 = new ListNode1(9);
    l1.next = l11;

    l11.next = new ListNode1(2);
    l11 = l11.next;

    l11.next = new ListNode1(1);

    ListNode1 l2 = new ListNode1(9);
    ListNode1 l22 = new ListNode1(9);
    l2.next = l22;

    //    l22.next = new ListNode1(2);
    //    l22 = l22.next;

    //    l22.next = new ListNode1(1);

    new Solution().addTwoNumbers(l1, l2);
  }
}

class ListNode1 {
  int val;
  ListNode1 next;

  ListNode1() {}

  ListNode1(int val) {
    this.val = val;
  }

  ListNode1(int val, ListNode1 next) {
    this.val = val;
    this.next = next;
  }
}

class Solution {
  public ListNode1 addTwoNumbers(ListNode1 l1, ListNode1 l2) {

    ListNode1 rn = new ListNode1();
    ListNode1 rn1 = rn;

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

      rn1.next = new ListNode1(sum % 10);
      rn1 = rn1.next;
    }

    if (reminder > 0) {
      rn1.next = new ListNode1(1);
    }

    return rn;
  }
}
