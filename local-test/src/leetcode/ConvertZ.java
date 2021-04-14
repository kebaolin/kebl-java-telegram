package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: Mar 23, 2021
 * Title: Z字形转换
 * Description: 
 * Filename: ConvertZ.java
 */
public class ConvertZ {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String ss = "PAYPALISHIRING";
		System.out.println(convert(ss, 4));

	}
	/* 
	 * input:ABCDEFGHIJKLMNOPQRST
	 * ---------------------
	 * A      G      M      S
	 * B   F H   L N    R T
	 * C E   I K   O Q
	 * D      J      P
	 * ---------------------
	 * output:AGMSBFHLNRTCEIKOQDJP
	 */
	private static String convert(String ss, int rowNumbers) {
		// TODO Auto-generated method stub
		if (rowNumbers < 2) {
			return ss;
		}
		// 创建一个StringBuilder的list，元素个数与行数一致，每一个元素代表一行
		// StringBuilder可以链式调用
		List<StringBuilder> rows = new ArrayList<>();
		// 取s长度和行的较小者，可能存在字符长度为3，行数为4
		for (int i = 0; i < Math.min(rowNumbers, ss.length()); i++) {
			rows.add(new StringBuilder());
		}
		// 起始行数以及增长量
		int curRows = 0, flag = -1;
		// 循环整个字符串
		for (int j = 0; j < ss.length(); j++) {
			// 将该字符放入到对应的行中
			rows.get(curRows).append(ss.charAt(j));
			// 如果行对应的是转折点，将增长量改变，从上到下为+1，从下到上为-1
			if (curRows ==0 || curRows == rowNumbers - 1) {
				flag = -flag;
			}
			// 改变行数
			curRows += flag;
		}
		// 读取list里面的数据，转换成String返回
		StringBuilder res = new StringBuilder();
		for (StringBuilder ret: rows) {
			res.append(ret);
		}
		return res.toString();
	}

}
