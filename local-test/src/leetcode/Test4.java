package leetcode;

import java.util.Random;

/**
 * Date: Apr 22, 2021
 * Title: 
 * Description: 
 * Filename: Test4.java
 */
public class Test4 {

	public static void main(String[] args) {
		Random ran = new Random();
		String aa = "013U20210421";
		int bb = ran.nextInt(999999999);
		int cc = ran.nextInt(99999);
		String dd = aa + bb + cc;
		System.out.println(dd);
	}

}
