package leetcode;

/**
 * Date: Apr 21, 2021
 * Title: 数字解码
 * Description: 一条包含字母 A-Z 的消息通过以下映射进行了 编码 ：

'A' -> 1
'B' -> 2
...
'Z' -> 26
要 解码 已编码的消息，所有数字必须基于上述映射的方法，反向映射回字母（可能有多种方法）。例如，"11106" 可以映射为：

"AAJF" ，将消息分组为 (1 1 10 6)
"KJF" ，将消息分组为 (11 10 6)
注意，消息不能分组为  (1 11 06) ，因为 "06" 不能映射为 "F" ，这是由于 "6" 和 "06" 在映射中并不等价。

给你一个只含数字的 非空 字符串 s ，请计算并返回 解码 方法的 总数 。

题目数据保证答案肯定是一个 32 位 的整数。

 * Filename: NumberDecodingSolution.java
 */
public class NumberDecodingSolution {

	public static void main(String[] args) {
		String s = "123";
		System.out.println(numberDecoding(s));
		System.out.println(numberDecodingNew(s));
	}
	
	// 解法：
	// 1、先考虑最后一位独立解码情况，不为0，即能独立解码，此方案的总数为dp[i-1]
	// 2、再考虑最后一位和前面一位能组合一起解码，即数字范围是10-26，此方案总数为dp[i-2]
	// 最后结果就是把两种方案的数量加起来即为解
	private static int numberDecoding(String s) {
		int length = s.length();
		int[] dp = new int[length + 1];
		// 空字符串有一种解码方法，解码成空字符串
		dp[0] = 1;
		// 循环，字符串下标从0开始，dp[1]代表下标为0的字符串的解法，所以从1开始循环，只到等于length
		for (int i = 1; i <= length; i++) {
			// 如果最后一位能单独解码，则解码总数等于i-1处的解码数量
			if (s.charAt(i-1) != 0) {
				dp[i] = dp[i-1];
			}
			// 如果最后一位和前面一位组合也能解码，则解码总数等于i-2处的解码数量+上面的解码数量
			// 和前面组合的条件就是i-2处为1和2，即数字范围是10-26
			if (i > 1 && s.charAt(i-2) != 0 && (10 * (s.charAt(i-2) - '0') + (s.charAt(i-1) - '0') <= 26)) {
				// 此处一并考虑了最后一位为0的情况，此时总量就是dp[i-2]处总量
				dp[i] += dp[i-2];
			}
		}
		return dp[length];
	}
	
	// 优化空间，用变量来代替数组
	// 题解中dp[i]只和dp[i-1],dp[i-2]有关，所以可以用三个变量来替代
	private static int numberDecodingNew(String s) {
		int length = s.length();
		// a = f[i-2], b=f[i-1], c=f[i]
		int a = 0, b = 1, c = 0;
		for (int i = 1; i <= length; i++) {
			if (s.charAt(i-1) != 0) {
				c = b;
			}
			if (i>1 && s.charAt(i-2)!=0 &&(10*(s.charAt(i-2) - '0') + (s.charAt(i-1) - '0') <= 26)) {
				c += a;
			}
			// 滚动数组，滚动起来
			a = b;
			b = c;
		}
		return c;
	}

}
