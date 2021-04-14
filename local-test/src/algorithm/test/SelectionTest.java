package algorithm.test;

import java.util.Arrays;

import algorithm.sort.Selection;

/**
 * Date: Mar 9, 2021
 * Title: 选择排序测试类
 * Description: 
 * Filename: SelectionTest.java
 */
public class SelectionTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Integer[] b = {4,5,6,2,3,1};
		Selection se = new Selection();
		se.sort(b);
		System.out.println(Arrays.deepToString(b));
	}

}
