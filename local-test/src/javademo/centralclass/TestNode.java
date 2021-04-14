package javademo.centralclass;

import javademo.centralclass.ListNode;
/**
 * Date: Mar 4, 2021
 * Title: 
 * Description: 
 * Filename: TestNode.java
 */
public class TestNode {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		ListNode l1 = new ListNode(2, new ListNode(4, new ListNode(3)));
//		System.out.println(l1.val);
//		System.out.println(l1.next.val);
//		ListNode l2 = l1.next;
//		System.out.println(l2.next.val);
		ListNode head = null, tail=null;
		head = tail = new ListNode(10);
		System.out.println(head.val);
		System.out.println(tail.val);
		tail.next = new ListNode(20);
		System.out.println(head.next.val);
		System.out.println(tail.next.val);
		tail = tail.next;
		tail.next = new ListNode(30);
		System.out.println(head.next.next.val);
		System.out.println(tail.next.val);

	}

}
