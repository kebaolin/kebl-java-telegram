package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Date: Mar 15, 2021
 * Title: 
 * Description: 
 * Filename: Test1.java
 */
public class Test1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a = {7,6,5,4,3,2,1};
		
		int left =0, right = 6;
		for (int i = left, j = i; i < right; j = ++i) {
			int ai = a[i+1];
			System.out.println("ai is" + ai);
			while (ai < a[j]) {
				System.out.println("enter");
				a[j+1] = a[j];
				System.out.println("first j is " + j);
				if (j-- == left) {
					break;
				}
				System.out.println("j is " + j);
			}
			System.out.println("j out is " + j);
			a[j+1] = ai;
		}
		System.out.println(Arrays.toString(a));
		System.out.println();
	}

}
