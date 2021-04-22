package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Date: Apr 21, 2021
 * Title: N皇后问题
 * Description: n 皇后问题 研究的是如何将 n 个皇后放置在 n×n 的棋盘上，并且使皇后彼此之间不能相互攻击。

给你一个整数 n ，返回所有不同的 n 皇后问题 的解决方案。

每一种解法包含一个不同的 n 皇后问题 的棋子放置方案，该方案中 'Q' 和 '.' 分别代表了皇后和空位。

 * Filename: NQueensSolution.java
 */
public class NQueensSolution {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

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
