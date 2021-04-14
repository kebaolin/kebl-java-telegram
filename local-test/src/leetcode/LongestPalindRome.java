package leetcode;

/**
 * Date: Mar 22, 2021
 * Title: 回文子串
 * Description: babad ====> bab
 * Filename: LongestPalindRome.java
 */
public class LongestPalindRome {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "babad";
		System.out.println(getLongestPalindRomeByDynamic(s));
		System.out.println(getLongestPalindRomeByCenterSpread(s));

	}
	// 动态规划解法
	static String getLongestPalindRomeByDynamic(String s) {
		int len = s.length();
		int start = 0, maxLength = 1;
		boolean[][] dp = new boolean[len][len];
		// 单个字串必定为true，其实不需要，其他状态不会参考此状态
		for (int i = 0; i < len; i++) {
			dp[i][i] = true;
		}
		// i严格小于j，遍历每一个元素
		for (int j = 1; j < len; j++) {
			// 遍历j之前的每一个元素，分别和j组成一个字串，因为动态扩展从内部往外扩展，即外部状态依赖内部状态
			// i从0开始，枚举最长的子串，或者从j-1开始，枚举最短的子串，都可以
			for (int i = 0; i < j; i++) {
				// 如果i，j处的元素相等
				if (s.charAt(i) == s.charAt(j)) {
					// 如果i，j区间的长度为3或2，因为i严格小于j，所以最短长度为2，那么此时状态必定为true
					if (j-i+1 < 4) {
						dp[i][j] = true;
					}
					// 长度大于3的区间，则依赖于内部缩小的区间的状态
					else {
						dp[i][j] = dp[i+1][j-1];
					}
				}
				// 如果i，j处元素不相等，则状态必定为false
				else {
					dp[i][j] = false;
				}
				// 判断此时的回文子串长度是否大于最大回文子串的长度，如果是更新长度以及起始位置
				if (dp[i][j]) {
					if (j-i+1 > maxLength) {
						maxLength = j-i+1;
						start = i;
					}
			}				
			}
		}
		// 从起始位置加上长度，切割字符串
		return s.substring(start, start+maxLength);
	}
	
	// 中心扩散解法
	static String getLongestPalindRomeByCenterSpread(String s) {
		int len = s.length();
		int start = 0, end = 0;
		// 遍历中心位
		for (int i = 0; i < len-1; i++) {
			// 以中心位向左右两边扩展，找出最长回文子串的首尾位置
			// 子串长度为奇数，左右中心都是自己
			int[] odd = centerSpread(s, i, i);
			// 子串长度为偶数，左右中心分别为自己和下一位
			int[] even = centerSpread(s, i, i+1);
			// 此时长度比最大的回文子串长度大，则更新起始位置
			if (odd[1] - odd[0] > end - start) {
				start = odd[0];
				end = odd[1];
			}
			// 此时长度比最大的回文子串长度大，则更新起始位置
			if (even[1] - even[0] > end - start) {
				start = even[0];
				end = even[1];
			}
		}
		return s.substring(start, end+1);
	}
	// 以中心位向左右扩展，判断一个串是不是回文子串
	private static int[] centerSpread(String s, int left, int right) {
		// TODO Auto-generated method stub
		// 边界为字符串首尾，如果元素相等，则继续向外扩展比较
		while (left >=0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
			left--;
			right++;
		}
		// 返回终止时候的位置
		// 跳出循环是left和right处元素不相等，所以要排除掉
		int[] central = {left+1, right-1};
		return central;
	}
	

}
