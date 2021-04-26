package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Date: Apr 21, 2021
 * Title: N皇后问题
 * Description: n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。

给你一个整数 n ，返回所有不同的 n 皇后问题 的解决方案。

每一种解法包含一个不同的 n 皇后问题 的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。

 * Filename: NQueensSolution.java
 */
public class NQueensSolution2 {

	public static void main(String[] args) {
		for (List<String> ss : solution(4)) {
			System.out.println(ss);
		}
		System.out.println(solution(4).size());
	}
	
	// 直接用结果queens来解，不需要额外的set
	private static List<List<String>> solution(int n){
		List<List<String>> solutions = new ArrayList<List<String>>();
		// 创建数组以及判断皇后是否冲突需要的集合
		// queens里面放的是皇后放置的位置，是列坐标，用一维数组表示，下标恰好代表行也代表放置的是第几个皇后，假设我们从0开始
		int[] queens = new int[n];
		// 从第一行开始放置
		backtrack(solutions, queens, n, 0);
		return solutions;
	}
	// 放置方法
	/**
	 * 
	 * @param solutions 结果集，是个list，里面的每一个元素也是一个list，每个元素代表一种解
	 * @param queens 用来存放一种解法的数组
	 * @param n 代表皇后的个数，如果是8，则棋盘就是8*8
	 * @param row 代表皇后放置在哪一行
	 */
	private static void backtrack(List<List<String>> solutions, int[] queens, int n, int row) {
		// n从0开始，如果最后一行已经遍历完位置了，该次递归结束
		if (row == n) {
			List<String> result = generateBoard(queens, n);
			solutions.add(result);
			return;
		}
		// 开始，i代表列，从每一行第一列开始试探
		for (int i = 0; i < n; i++) {
			boolean tag = false;
			// 和已经放置的所有皇后比较，查看是否冲突
			for (int j = 0; j < row; j++) {
				// 如果在同一列，i == queens[j]
				// 如果在斜线上，Math.abs(row - j) == Math.abs(i - queens[j])，解释：两个元素，行下标之差等于列下标之差（绝对值比较），即在一条斜线上
				if (i == queens[j] || Math.abs(row - j) == Math.abs(i - queens[j])) {
					tag = true;
					break;
				}
			}
			// 上面判断有冲突，则这个位置不能放，继续往下一列试探
			if (tag) {
				continue;
			}
			// 如果不冲突，就放在这
			queens[row] = i;
			// 放置下一个皇后，即往下一行，递归开始，row+1
			backtrack(solutions, queens, n, row+1);
			// 回溯，继续试探上一行的每一个位置
			// 举例，上面递归是第8行遍历完毕，出来开始试探第7行的每个位置
			queens[row] = -1;
		}		
	}
	// 创建一个能打印结果的方法
	private static List<String> generateBoard(int[] queens, int n){
		// 创建一个存放结果的list
		List<String> board = new ArrayList<>();
		// 开始循环
		for (int i = 0; i < n; i++) {
			// n皇后就有n列
			char[] row = new char[n];
			// 每列先预置成'.'，代表不是放置皇后的位置
			Arrays.fill(row, '.');
			// 然后将放置皇后的那一列，置成Q，代表这个地方是皇后
			// queens[i]代表第i+1个皇后放在在哪一列，直接将这一列上的'.'替换成Q
			row[queens[i]] = 'Q';
			// 将这一行加入到结果集中
			// 等所有行都加入到结果集中以后，就得到一种解法的结果
			board.add(new String(row));
		}
		return board;
	}

}
