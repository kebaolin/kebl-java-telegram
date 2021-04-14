package algorithm.test;

import java.util.Arrays;

import algorithm.sort.Shell;

/**
 * Date: Mar 10, 2021
 * Title: 
 * Description: 
 * Filename: ShellTest.java
 */
public class ShellTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Integer[] a = {4,5,6,2,3,1};
		Shell sh = new Shell();
		sh.sort(a);
		System.out.println(Arrays.toString(a));

	}

}
