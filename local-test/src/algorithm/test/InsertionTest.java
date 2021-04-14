package algorithm.test;

import java.util.Arrays;

import algorithm.sort.Insertion;

/**
 * Date: Mar 10, 2021
 * Title: 插入排序测试类
 * Description: 
 * Filename: InsertionTest.java
 */
public class InsertionTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Integer[] a = {4,5,6,2,3,1};
		Insertion ins = new Insertion();
		ins.sort(a);
		System.out.println(Arrays.deepToString(a));

	}

}
