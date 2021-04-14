package leetcode;

import java.util.Arrays;

/**
 * Date: Mar 23, 2021
 * Title: 
 * Description: 
 * Filename: RegulerMatch.java
 */
public class RegulerMatch {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String ss = "mmm";
		String p = ".*";
		System.out.println(isMatch(ss, p));

	}

	private static boolean isMatch(String ss, String p) {
		int m = ss.length(), n = p.length();
		// 以*开头，不匹配
		if (n > 0 && p.charAt(0) == '*') {
			return false;
		}
		// 构造一个二维数组，长度分别比s和p多1，dp[0]开头用来存放空串
		boolean[][] dp = new boolean[m+1][n+1];
		// s和p都为空
		dp[0][0] = true;
		// 填每个数组的第一列dp[i][0]，即p为空的时候，空和ss里面任何字符都不匹配，所以都为false
		// ////创建二维数组的时候有默认值false，所以这一部分是多余的/////
		for (int i = 1; i <=m; i++) {
			dp[i][0] = false;
		}
		// 填dp[0][j]的每一个元素，即ss为空的时候
		// //////这部分和下面一部分是重复的填值，放一起写就可以了，把下面的i从0开始就可以了//////
		for (int j =1; j <= n; j++) {
			// 当该元素为*的时候，可以放弃该位以及前一位不参与匹配，取dp[j-2]的结果
			if (p.charAt(j-1) == '*') {
				dp[0][j] = dp[0][j-2];
			}
			else {
				dp[0][j] = false;
			}
		}
		// 填其他格子
		for (int i=1; i <= m; i++ ) {
			for (int j = 1; j <= n; j++) {
				// ss和p最后一位相等，或者p最后一位为'.'可以匹配任意字符，参考前面一位的匹配结果
				if (ss.charAt(i-1) == p.charAt(j-1) || p.charAt(j-1) == '.') {
					dp[i][j] = dp[i-1][j-1];
				}
				// 如果不相等，但是p最后一位为*，还可以继续比较
				else if (p.charAt(j-1) == '*') {
					// *的前面一位与ss的最后一位相等
					if (p.charAt(j-2) == ss.charAt(i-1) || p.charAt(j-2) == '.') {
						// 匹配一次或多次，例：ss为aabccc或aabc，p为aabc*，去掉ss的一个c，继续匹配dp[i-1][j]
						// 匹配0次，例：ss为abc，p为abcc*，j-2=i-1处，都为c，但是c*参与0次匹配，继续匹配dp[i][j-2]
						dp[i][j] = dp[i-1][j] || dp[i][j-2];
					}
					// *的前面一位与ss的最后一位不相等，去掉*和前面一位
					else {
						dp[i][j] = dp[i][j-2];
					}
				}
				// 有默认值，也是多余的
				else {
					dp[i][j] = false;
				}
			}
		}
		System.out.println(Arrays.deepToString(dp));
		return dp[m][n];
	}

}
