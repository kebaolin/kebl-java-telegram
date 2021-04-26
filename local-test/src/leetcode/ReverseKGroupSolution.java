package leetcode;

/**
 * Date: Apr 26, 2021
 * Title: K个一组翻转一个链表
 * Description: 给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。

k 是一个正整数，它的值小于或等于链表的长度。

如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。

进阶：
你可以设计一个只使用常数额外空间的算法来解决此问题吗？
你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。

 * Filename: ReverseKGroupSolution.java
 */
public class ReverseKGroupSolution {

	public static void main(String[] args) {
		ListNode head = new ListNode(1);
		ListNode l1 = new ListNode(2);
		head.next = l1;
		ListNode l2 = new ListNode(3);
		l1.next = l2;
		ListNode l3 = new ListNode(4);
		l2.next = l3;
		ListNode l4 = new ListNode(5);
		l3.next = l4;
		//翻转前
		ListNode pre = head;
		while (pre != null) {
			System.out.print(pre.val + "->");
			pre = pre.next;
		}
		System.out.println();
		ListNode newHead = reverseKGroup(head , 2);
		//翻转后
		while (newHead != null) {
			System.out.print(newHead.val + "->");
			newHead = newHead.next;
		}
	}
	
	private static ListNode reverseKGroup(ListNode head, int k) {
		// 创建一个哑节点，用于指向头节点
		ListNode dummy = new ListNode(-1);
		dummy.next = head;
		// 创建一个前置节点，首先指向哑节点
		ListNode pre = dummy;
		// 开始循环，退出条件为head为空
		while (head != null) {
			// 从前置节点开始
			ListNode tail = pre;
			// 遍历k次，即为k个节点
			for (int i = 0; i < k; i++) {
				tail = tail.next;
				// 如果链表的节点不足k个，此时可以直接返回
				if (tail == null) {
					return dummy.next;
				}
			}
			// 将一组（k个）节点进行翻转操作，拿到翻转后的头尾节点
			ListNode[] reversedListNode = reverseOneList(head, tail);
			//新的头尾节点
			head = reversedListNode[0];
			tail = reversedListNode[1];
			// 将前置节点与翻转后的链表头节点连接起来
			pre.next = head;
			// 将pre指向新的尾部，类似于下一个哑节点
			pre = tail;
			// 将head移动到新的头节点，即尾节点的下一个节点
			head = tail.next;
		}
		return dummy.next;
	}
	
	// 翻转一组节点方法
	private static ListNode[] reverseOneList(ListNode head, ListNode tail) {
		// 翻转操作需要一个前置节点和当前节点，两个指针
		// pre一般是哑节点，此处翻转后需要连接后面节点，所以pre指向下一组的头节点，即当前组尾节点的下一个节点
		ListNode pre = tail.next;
		ListNode cur = head;
		// 当pre达到tail节点的时候，翻转已经完成
		while (pre != tail) {
			// 获取到下一个节点
			ListNode next = cur.next;
			// 将当前节点的next指向前置节点
			cur.next = pre;
			// 前置节点往后移动
			pre = cur;
			// 当前节点往后移动
			cur = next;
		}
		// 将翻转后的这一组链表返回，翻转后的节点尾部已经连接，头部尚未连接
		// 翻转后头节点即为翻转前的尾节点，将该节点返回用于连接
		return new ListNode[] {tail, head};
	}

}
