package leetcode;

/**
 * Date: Apr 26, 2021
 * Title: ListNode定义
 * Description: 用于链表算法中调用
 * Filename: ListNode.java
 */
public class ListNode {
	int val;
	ListNode next;
	ListNode(){};
	ListNode(int val){this.val = val;}
	ListNode(int val, ListNode next){
		this.val = val;
		this.next = next;
	}
}
