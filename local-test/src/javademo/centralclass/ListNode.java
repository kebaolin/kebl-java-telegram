package javademo.centralclass;

import java.util.HashMap;

/**
 * Date: Mar 4, 2021
 * Title: 
 * Description: 
 * Filename: ListNode.java
 */

public class ListNode {
	public static void main(String[] args) {
		HashMap<String, Integer> map = new HashMap<>();
		map.put("foo", map.getOrDefault("foo", 0)+5);
		System.out.println(map.getOrDefault("foo", 0)+10);
	}
	int val;
	ListNode next;
	ListNode(int val){
		this.val = val;
	}
	ListNode(int val, ListNode next){
		this.val = val;
		this.next = next;
	}
}
