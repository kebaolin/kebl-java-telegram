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
public class NQueensSolution {

	public static void main(String[] args) {
		for (List<String> ss : solution(8)) {
			System.out.println(ss);
		}
		System.out.println(solution(8).size());
	}
	
	// 用set来解
	private static List<List<String>> solution(int n){
		List<List<String>> solutions = new ArrayList<List<String>>();
		// 创建数组以及判断皇后是否冲突需要的集合
		int[] queens = new int[n];
		// 列集合
		Set<Integer> columns = new HashSet<>();
		// 左上到右下斜线的集合，存储的是行下标和列下标之差
		Set<Integer> lefts = new HashSet<>();
		// 右上到左下斜线的集合，存储的是行下标和列下标只和
		Set<Integer> rights = new HashSet<>();
		// 从第一行开始放置
		backtrack(solutions, queens, n, 0, columns, rights, lefts);
		return solutions;
	}
	// 放置方法
	/**
	 * 
	 * @param solutions 结果集，是个list，里面的每一个元素也是一个list，每个元素代表一种解
	 * @param queens 用来存放一种解法的数组
	 * @param n 代表皇后的个数，如果是8，则棋盘就是8*8
	 * @param row 代表皇后放置在哪一行
	 * @param columns 一个存放列的集合，某一列放置了皇后，就将该列加入到该集合中
	 * @param rights  存放行下标和列下标之和的集合，用来代表从右上到左下斜线
	 * @param lefts 存放行下标和列下标之差的集合，用来代表从左上到右下斜线
	 */
	private static void backtrack(List<List<String>> solutions, int[] queens, int n, int row, Set<Integer> columns, Set<Integer> rights, Set<Integer> lefts) {
		// n从0开始，如果最后一行已经遍历完位置了，该次递归结束
		if (row == n) {
			List<String> result = generateBoard(queens, n);
			solutions.add(result);
			return;
		}
		// 开始放置新的皇后，这里的i表示列
		for (int i = 0; i < n; i++) {
			// 校验新放置的皇后是否与已经放置的皇后冲突
			// 1、校验是否放在同一列了
			if (columns.contains(i)) {
				continue;
			}
			// 2、校验是否在左斜线上，方向一为：从左上到右下
			// 斜线为从左上到右下方向，同一条斜线上的每个位置满足行下标与列下标之差相等，例如(0, 0) 和 (3,3) 在同一条方向一的斜线上。
			// 因此使用行下标与列下标之差即可明确表示每一条方向一的斜线
			int left  = row - i;
			// 如果差存在，说明该斜线上已经存在皇后了，不能放，继续往下一列放
			if (lefts.contains(left)) {
				continue;
			}
			// 3、校验是否在右斜线上，方向二为：从右上到坐下
			// 斜线为从右上到左下方向，同一条斜线上的每个位置满足行下标与列下标之和相等，例如(3, 0)和（1，2）在同一条方向的斜线上。
			// 因此使用行下标与列下标之和即可明确表示每一条方向二的斜线
			int right = row + i;
			// 如果和存在，说明该斜线上已经存在皇后了，不能放，继续往下一列放
			if (rights.contains(right)) {
				continue;
			}
			// 如果上面校验都通过，说明该皇后可以放在此位置
			// 用一个一维数组而不是二维数组，因为行刚好可以代替放置的皇后的个数，比如第一个皇后，row=0
			queens[row] = i;
			// 将列，斜线都加到集合中
			columns.add(i);
			lefts.add(left);
			rights.add(right);
			// 下一行开始放置皇后，开启递归，仅改变row为row+1即可
			backtrack(solutions, queens, n, row+1, columns, rights, lefts);
			// 下一行所有能放置位置遍历完成后，我们需要开启回溯，将上一行放置的皇后位置拿掉，开始往下一个位置试探
			// 例如第8行遍历完成以后，我们需要返回第7行，把第7行的所有位置都试一遍
			queens[row] = -1; //用-1仅代表该行未放置，任意数字，只要不是列就行
			columns.remove(i);
			lefts.remove(left);
			rights.remove(right);
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
