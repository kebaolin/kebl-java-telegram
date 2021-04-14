package leetcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Date: Apr 13, 2021
 * Title: 全排列
 * Description: [1,2,3]====>[[1, 2, 3], [1, 3, 2], [2, 1, 3], [2, 3, 1], [3, 1, 2], [3, 2, 1]]
 * Filename: FullPermuteSolution.java
 */
public class FullPermuteSolution {

	public static void main(String[] args) {
		int[] nums = {1,2,3};
		System.out.println(fullPermute(nums));
	}
	
	public static List<Deque<Integer>> fullPermute(int[] nums){
		int len = nums.length;
		// 状态变量，是个栈，全局只维护一个状态变量，往下一层遍历的时候入栈，撤销的时候出栈
		Deque<Integer> path = new ArrayDeque<Integer>();
		// 用一个数组来维护，哪些数字已经遍历过
		boolean[] used = new boolean[len];
		// 结果集
		List<Deque<Integer>> res = new ArrayList<>();
		if (len == 0) {
			return res;
		}
		// 深度遍历这颗树，每个叶子节点就是结果集的一个子集
		dfs(nums, len, 0, path, used, res);
		return res;
	}

	private static void dfs(int[] nums, int len, int depth, Deque<Integer> path, boolean[] used, List<Deque<Integer>> res) {
		// 遍历到叶子节点，这次遍历已经结束，添加到结果集
		if(depth == len) {
			// 对象的引用，需要做一次拷贝
			res.add(new ArrayDeque<Integer>(path));
		}
		// 通过循环来构建一个递归
		for (int i = 0; i < len; i++) {
			if (!used[i]) {
				// 取当前数字
				path.add(nums[i]);
				// 已取，改变状态
				used[i] = true;
				// 递归下一个，往下一层
				System.out.println("递归之前==>" + path);
				dfs(nums, len, depth+1, path, used, res);
				// 递归出来，重置状态
				used[i] = false;
				path.removeLast();
				System.out.println("递归之后==>" + path);
			}
		}
	}

}
