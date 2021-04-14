package leetcode;

import java.util.Stack;

/**
 * Date: Apr 7, 2021
 * Title: 最长有效括号
 * Description: )()())====>4
 * Filename: LongestValidParentheses.java
 */
public class LongestValidParentheses {

	public static void main(String[] args) {
		String s  = ")()())";
		System.out.println(getlongestValidParentheses(s));
		System.out.println(getlongestValidParenthesesByStack(s));
		System.out.println(getlongestValidParenthesesByIndex(s));
	}
	
	// 动态规划
	private static int getlongestValidParentheses(String s) {
		int size = s.length();
		int[] dp = new int[size];
		// 初始化接过
		int ans = 0;
		// 如果当前元素是'('，则dp[i]为0，只考虑')'的情况
		for (int i = 1; i < size; i++) {
			if (s.charAt(i) == ')') {
				// ...()格式
				if (s.charAt(i-1) == '(') {
					// 考虑边界情况，（）格式
					dp[i] = i-2 >= 0 ? dp[i-2] + 2 : 2;
				}
				// ...((...))格式
				// (...))格式dp[i]为0
				// ....)(...))格式dp[i]也为0
				else if (i-dp[i-1]>0 && dp[i-dp[i-1]-1] == '(') {
					// 注意边界，((...))格式
					dp[i] = i-dp[i-1] >=2 ? dp[i-1] + 2 + dp[i-dp[i-1]-2] : dp[i-1] + 2;
				}
			}
			// 每次取最大值更新结果
			ans = Math.max(ans, dp[i]);
		}
		return ans;
	}
	
	// 用栈来解，栈底永远保留的是未匹配到的最后一个右括号
	// 栈里面存放的都是下标值
	private static int getlongestValidParenthesesByStack(String s) {
		int ans = 0;
		Stack<Integer> stack = new Stack<>();
		// 放个-1，充当右括号
		// 如果第一个元素是右括号，会将他出栈，同时新括号入栈
		// 如果第一个元素是左括号，直接入栈
		stack.push(-1);
		for (int i = 0; i < s.length(); i++) {
			// 如果是左括号，直接入栈
			if (s.charAt(i) == '(') {
				stack.push(i);
			}
			// 如果是右括号，弹出栈顶和他匹配，同时计算此时的有效长度
			else {
				stack.pop();
				// 如果栈为空，说明右括号弹出去了，把此时的右括号入栈，此右括号即为当前未匹配到的最后一个右括号
				if (stack.isEmpty()) {
					stack.push(i);
				} else {
					// 栈里面还有元素，此时计算有效长度，当前元素下标减去新栈顶的值，即为有效长度
					ans = Math.max(ans, i-stack.peek());
				}
			}
		}
		return ans;
	}
	
	// 正向逆向遍历解法
	private static int getlongestValidParenthesesByIndex(String s) {
		int size = s.length();
		int left = 0, right = 0, ans = 0;
		// 从左向右遍历一次，遇到左括号，left++，遇到右括号right++
		for (int i = 0; i < size; i++) {
			if (s.charAt(i) == ')') {
				right++;
			} else {
				left++;
			}
			// 左右括号相等的时候，计算一次长度，即为当前左或右括号的长度*2
			if (left == right) {
				ans = Math.max(ans, left * 2);
			} 
			// 如果右括号大于左括号了，左边不可能再找到与他匹配的左括号了，证明最大长度在此处断开了，将left，right重置后继续遍历
			// 举例：）开头，()())格式
			else if(right > left) {
				left = right = 0;
			}
		}
		// 上面算法当左扩号一直大于右括号的时候，永远不会计算长度，例如：(((())
		// 解决方法为从右向左再遍历一次
		// 遍历之前记得将left，right重置
		left = right = 0;
		for (int j = size-1; j >= 0; j--) {
			if (s.charAt(j) == ')') {
				right++;
			} else {
				left++;
			}
			// 左右括号相等的时候，计算一次长度，即为当前左或右括号的长度*2
			if (left == right) {
				ans = Math.max(ans, left * 2);
			} 
			// 同上面相反，当左括号多了，右边不可能再有右括号与他匹配，所以重置left，right
			else if(left > right) {
				left = right = 0;
			}
		}
		return ans;
	}
}
