package com.success.leet.medium;

public class LinkedListAddTwoNumbers {

	public static void main(String[] args) {
		ListNode l1 = new ListNode(2);
		ListNode l11 = new ListNode(4);
		ListNode l12 = new ListNode(3);
		// l1.next = l11;
		// l11.next = l12;

		ListNode l2 = new ListNode(5);
		ListNode l21 = new ListNode(6);
		ListNode l22 = new ListNode(4);
		// l2.next = l21; l21.next = l22;

		addTwoNumbers(l1, l2);
	}

	public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {

		ListNode mainNode = null;
		ListNode childNode = null;
		int carry = 0;
		while (l1 != null || l2 != null) {

			int val1 = l1 != null ? l1.val : 0;
			int val2 = l2 != null ? l2.val : 0;
			int result = val1 + val2 + carry;
			carry = result / 10;
			int target = result != 0 ? result % 10 : result;

			if (mainNode == null) {
				mainNode = new ListNode(target);
			} else if (childNode == null) {
				childNode = new ListNode(target);
				mainNode.next = childNode;
			} else {
				ListNode anotherChildNode = new ListNode(target);
				childNode.next = anotherChildNode;
				childNode = anotherChildNode;
			}
			l1 = l1 != null ? l1.next : null;
			l2 = l2 != null ? l2.next : null;
		}
		if (carry > 0) {
			if (childNode == null) {
				childNode = new ListNode(carry);
				mainNode.next = childNode;
			} else {
				ListNode anotherChildNode = new ListNode(carry);
				childNode.next = anotherChildNode;
			}
		}
		return mainNode;
	}
}
