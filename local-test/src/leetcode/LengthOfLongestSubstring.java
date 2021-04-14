package leetcode;

import java.util.HashSet;

/**
 * Date: Mar 8, 2021
 * Title: 无重复字符的最长子串
 * Description: abcabccc ====> 3
 * Filename: LengthOfLongestSubstring.java
 */
public class LengthOfLongestSubstring{
	public static void main(String[] args) {
		String ss = "abcabccc";
		int result = lengthOfLongestSubstring(ss);
		System.out.println(result);
		
	}
	// 采用滑动窗口
	static int lengthOfLongestSubstring(String s) {
		// 创建一个set用来存放扫描到到单个字符
		HashSet<Character> hashSet = new HashSet<>();
		int n = s.length();
		// 滑动指针，初始为-1
		int ans = 0, rk = -1;
		// 遍历每个字符，找出从每个字符为起点的最长字串
		for (int i = 0; i < n; i++) {
			if (i != 0)
				// 字符遍历往后移动一个，则从set里面剔除一个元素，确定新的起点位置
				hashSet.remove(s.charAt(i - 1));
			// 指针没有移动到最后一个字符，且这个字符不在set里面，加入到set里，同时指针右移一位
			// 一直循环到遇到该字符已经存在set里了，结束循环，得到本次i起点的最大长度
			while (rk + 1 < n && !hashSet.contains(s.charAt(rk + 1))) {
				hashSet.add(s.charAt(rk + 1));
				rk += 1;
			}
			// 计算此时的最大长度为rk-i+1
			ans = Math.max(ans, (rk -i +1));
		}
		return ans;
	}

}
