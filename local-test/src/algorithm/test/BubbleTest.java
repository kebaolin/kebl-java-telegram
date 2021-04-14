package algorithm.test;

import java.util.Arrays;

import algorithm.sort.Bubble;

/**
 * Date: Mar 9, 2021
 * Title: 冒泡排序测试类
 * Description: 
 * Filename: BubbleTest.java
 */
public class BubbleTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Integer[] a = {4,5,6,2,3,1};
		Bubble bb = new Bubble();
		bb.sort(a);
		System.out.println(Arrays.toString(a));

	}

}
