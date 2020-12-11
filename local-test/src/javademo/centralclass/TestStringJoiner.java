package javademo.centralclass;

import java.util.StringJoiner;

/**
 * Date: Dec 11, 2020
 * Title: 演示StringJoiner的使用
 * Description: StringJoiner指定分隔符来分隔一个数组，还可以指定开头和结尾；
 *                       String.join()方法内部调用来StringJoiner；
 * Filename: TestStringJoiner.java
 */
public class TestStringJoiner {
	public static void main(String[] args) {
		String[] ss = {"Bob", "Kel", "Jin"};
		StringJoiner sj = new StringJoiner(", ", "Hello ", "!");  //指定开头和结尾
		for (String s : ss) {
			sj.add(s);
		}
		System.out.println(sj.toString());
		
		//String.join()，不能指定开头和结尾
		System.out.println(String.join("**", ss));  //String的静态方法join，内部也是调用的StringJoiner
	}

}
