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
public class NQueensSolution3 {

	public static void main(String[] args) {
		for (List<String> ss : solution(8)) {
			System.out.println(ss);
		}
		System.out.println(solution(8).size());
	}
	
	// 用位运算来解
	private static List<List<String>> solution(int n){
		List<List<String>> solutions = new ArrayList<List<String>>();
		// 创建数组以及判断皇后是否冲突需要的集合
		int[] queens = new int[n];
		// 从第一行开始放置
		backtrack(solutions, queens, n, 0, 0, 0, 0);
		return solutions;
	}
	// 放置方法
	/**
	 * 
	 * @param solutions 结果集，是个list，里面的每一个元素也是一个list，每个元素代表一种解
	 * @param queens 用来存放一种解法的数组
	 * @param n 代表皇后的个数，如果是8，则棋盘就是8*8
	 * @param row 代表皇后放置在哪一行
	 * @param columns 用一个整数表示列，二进制位1表示被占用，0表示未被占用
	 * @param rights  用一个整数表示右上到左下，二进制位1表示被占用，0表示未被占用
	 * @param lefts 用一个整数表示左上到右下，二进制位1表示被占用，0表示未被占用
	 */
	private static void backtrack(List<List<String>> solutions, int[] queens, int n, int row, int columns, int rights, int lefts) {
		// n从0开始，如果最后一行已经遍历完位置了，该次递归结束
		if (row == n) {
			List<String> result = generateBoard(queens, n);
			solutions.add(result);
			return;
		}
		// 获取可以放置皇后的位置
		// 1、列及斜线三个整数做或运算，二进制上为1的位置就保留下来了，即不能放置皇后的位置
		// 2、为了便于运算，我们需要将可以放置皇后的位置变成1，不能放置皇后的位置变为0，将第一步得到的结果取反然后与2的n次方-1做与运算，即可得到需要的结果
		int availablePostions = ((1<< n) - 1) & (~(columns|rights|lefts));
		// 开始遍历放置皇后
		while (availablePostions != 0) {
			//取第一个可以放置皇后的位置，将皇后放置
			// n和-n做与运算，可以得到一个只保留了一个低位（从最低位往高位第一个1出现的地方）为1的数，高位部分全部置为0，所以该数也必定是2的x次方
			// 举例：n=1100，与-n做与运算后结果为0100
			int position = availablePostions & (-availablePostions);
			// 该位置放置皇后，需要置为0
			// n和n-1做与运算，可以将低位（从最低位往高位第一个1出现的地方）的1变成0，高位不变
			// 举例：n=1100，与n-1做与运算后的结果为1000
			availablePostions = availablePostions & (availablePostions - 1);
			// 获取具体放置到哪一列了
			// Integer.bitCount方法为获取一个二进制位里面包含了多少个1；
			// 而我们获取到的position一定是2的次方类型，所以2的次方-1后得到的二进制位是低位全是1，原本为1的位置变为0，此时得到的1的个数就是
			// 原本position那个1的具体位置，举例n=1000=2**3，此时1的位置为3（从0开始），-1后为0111，共3个1，即为原本1的位置
			int column = Integer.bitCount(position-1);
			queens[row] = column;
			// 开始放置下一行，row+1
			// 进入下一行的时候，确定不能放置的列，斜线
			// 使用原本的值与position做或运算，因为position那个1就是本次放置的位置，而原本columns, rights, lefts就是用1来表示不能放置的位置
			// 做或运算后左移一位，表示左上到右下斜线的位置，右移一位，表示从右上到左下到位置
			backtrack(solutions, queens, n, row+1, columns|position, (rights|position)<<1, (lefts|position)>>1);
			// 递归完成无需状态重置，因为我们在进入下一行的时候已经确定了状态
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
