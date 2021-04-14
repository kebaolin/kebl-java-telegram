package leetcode;

import java.util.Arrays;

/**
 * Date: Mar 15, 2021
 * Title: 
 * Description: 
 * Filename: Test2.java
 */
public class Test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(Integer.toBinaryString(160));
		System.out.println(Integer.toBinaryString(160>>3));
		System.out.println(Integer.toBinaryString(160>>6));
		int[] a = new int[300];
		for (int i = 100,j = 0; i >90; i--, j++) {
			a[j] =i;
		}
		
		for (int i = 1,j = 10; i < 90; i++, j++) {
			a[j] =i;
		}
		System.out.println(Arrays.toString(a));
		Arrays.sort(a);
		System.out.println(Arrays.toString(a));
		boolean[][] dp = new boolean[3][1];
		System.out.println(Arrays.deepToString(dp));
		System.out.println(dp[2][0]);

	}

}
