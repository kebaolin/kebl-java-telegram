package leetcode;

import java.util.Arrays;

/**
 * Date: Mar 23, 2021
 * Title: 
 * Description: 
 * Filename: RegulerMatch.java
 */
public class RegulerMatchNew {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "aaa";
		String p = "";
		System.out.println(isMatch(s, p));

	}

	private static boolean isMatch(String s, String p) {
		int m = s.length(), n = p.length();
		// 以*开头，不匹配
		if (n > 0 && p.charAt(0) == '*') {
			return false;
		}
		// 构造一个二维数组，长度分别比s和p多1，dp[0]开头用来存放空串
		boolean[][] dp = new boolean[m+1][n+1];
		// s和p都为空
		dp[0][0] = true;
		// 填其他格子，dp的索引i，j比s和p的索引多一位，即dp[i][j]对应的是s[i-1]和p[j-1]处的状态
		for (int i=0; i <= m; i++ ) {
			for (int j = 1; j <= n; j++) {
				// 以*号结尾
				if (p.charAt(j-1) == '*') {
					// 如果s(不为空)最后一位和p的*号前一位相等
					// 分两种情况
					// 1、匹配一次或多次，例：s为aabccc或aabc，p为aabc*，去掉s的一个c，继续匹配dp[i-1][j]
					// 2、匹配0次，例：s为abc，p为abcc*，p[j-2]=ss[i-1]处，都为c，但是c*参与0次匹配，继续匹配dp[i][j-2]
					if (i > 0 && (s.charAt(i-1) == p.charAt(j-2) || p.charAt(j-2) == '.')){
						dp[i][j] = dp[i][j-2] || dp [i-1][j];
					}
					// s为空或者p倒数第二位与s最后一位不相等，则s不动，p去掉*号和前面一位，继续匹配
					else {
						dp[i][j] = dp[i][j-2];
					}
				}
				else {
					// 如果ss不为空，p结尾和s结尾相同或者p结尾是.号可以任意匹配，则依赖与前面一位的状态
					if (i > 0 && (s.charAt(i-1)== p.charAt(j-1) || p.charAt(j-1) == '.')) {
						dp[i][j] = dp[i-1][j-1];
					}
				}
			}
		}
		return dp[m][n];
	}

}
