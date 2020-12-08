package javademo.base;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Title: 数组操作
 * Description: 演示如何操作数组，一维及多维数组转换为字符串
 * Filename: TestArrays.java
 */
public class TestArrays {
	public static void main(String[] args) {
//		int[] ns = {1, 2, 3, 4, 5}; 
//		int[] ns1 = {};
//		Set<String[][]> dejaVu = new HashSet<String[][]>();
//		String[][] ns2 ={ {"xyz", "abc", "def", "ggg"}, {"hhb", "hjn", "mkl"}, {"dcv", "wds", "qsa"}};
//		String[] ns3 = {"xyz", "abc", "def", "ggg"};
//		System.out.println(Arrays.toString(ns));
//		System.out.println(Arrays.toString(ns1));
//		System.out.println(Arrays.deepToString(ns2));
//		System.out.println(Arrays.deepToString(ns3));
//		System.out.println(ns2.getClass());
//		System.out.println(Object[].class);
		int[][] scores = {{87,98,98}, {97,76,87}, {98,98,99},{100,95,96,86}};
		int i = 0;
		double sum =0;
		double avg;
		for (int[] sr : scores) {
			for (int score : sr) {
				sum += score;
				i++;
			}
		}
		System.out.println(i);
		System.out.println(sum);
		avg = sum /i;
		System.out.println(avg);
		System.out.printf("学生的平均分为: %.2f", avg);
	}

}
