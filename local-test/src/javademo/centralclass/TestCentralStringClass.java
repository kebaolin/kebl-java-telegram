package javademo.centralclass;

import java.util.Arrays;

/**
 * Date: Dec 10, 2020
 * Title: 演示核心类String
 * Description: 演示String不可变性；
 *                       String比较使用equals，搜索子串，提取子串；
 *                       去掉String首位空白；
 *                       替换子串，分割字符串，拼接字符串，格式化字符串（formatted，format）；
 *                       类型转换；
 *                       字符编码；
 * Filename: TestCentralStringClass.java
 */
public class TestCentralStringClass {
	public static void main(String[] args) {
		//字符串不变性
		String s = "Hello";
		System.out.println(s);
		s = s.toUpperCase();  //生成一个新的字符串对象，s指向新的对象，原字符串“Hello“没变，只是不能通过s访问到
		System.out.println(s); //HELLO
		
		//字符串比较
		String s1 = "hello";
		String s2 = "hello";
		String s3 = "HELLO".toLowerCase();
		String s4 = "Hello";
		System.out.println(s1 == s2);  //java在编译期把相同的字符串当成一个对象放入常量池，所以s1和s2的引用是相等的
		System.out.println(s1.equals(s2)); //true，字符串内容的比较
		System.out.println(s1 == s3); //false，s1和s3是不同的对象
		System.out.println(s1.equals(s3)); //true，字符串内容的比较
		System.out.println(s1.equalsIgnoreCase(s4)); //true
		
		//搜索、提取字符串
		System.out.println(s1.contains("ll")); //true
		System.out.println(s1.indexOf("ll")); //2
		System.out.println(s1.startsWith("he")); //true
		System.out.println(s1.endsWith("lo")); //true
		System.out.println(s1.substring(2)); //llo
		System.out.println(s1.substring(2, 3)); //l
		
		//去掉首尾空白
		String s5 = " hello ";
		System.out.println(s5.trim()); //hello,生成一个新的字符串对象，原字符串没有改变
		System.out.println(s5.isEmpty()); //false
		System.out.println("".isEmpty()); //true
		System.out.println(" ".isEmpty()); //false
		
		//替换子串
		System.out.println(s1.replace("l", "w")); //hewwo
		System.out.println(s1.replace("lo", "~~")); //hel~~
		String s6 = "A,,;B;C ,D";
		System.out.println(s6.replaceAll("[\\,\\;\\s]+", ",")); //A,B,C,D
		
		//分割、拼接、格式化字符串
		String s7 = "A,B,C,D";
		String[] ss = s7.split("\\,");
		System.out.println(Arrays.toString(ss)); //[A, B, C, D]
		System.out.println(String.join("***", ss)); //A***B***C***D
		System.out.println(String.format("Hi, %s, your score is %d", "xiaoming", 88));
		
		//转换为char[]
		char[] ss1 = s1.toCharArray(); //{"h","e","l","l","o"}
		String s8 = new String(ss1);
		System.out.println(s8); //hello
		ss1[0] = 'm';
		System.out.println(s8); //hello,改变了char数组，并没有改变s8的值，因为在创建s8对象的时候，是copy了一份传进来的数组，而不是直接引用
		
		//String可变性举例
		int[] scores = {100,99,88,87};
		Score sr = new Score(scores);
		sr.printScores(); //[100, 99, 88, 87]
		scores[0] = 97;
		sr.printScores(); //[97, 99, 88, 87],变了，因为创建Score实例对象的时候是直接引用的数组scores，当数组发生变化，实例对象的字段也会变化
	}
}


//string可变性举例
class Score {
    private int[] scores;
    public Score(int[] scores) {
//    	this.scores = scores; //直接引用数组，数组变化，实例字段也会变化
    	this.scores = scores.clone(); //copy一份数组来实例化，当数组变化，不会影响实例
    }

    public void printScores() {
        System.out.println(Arrays.toString(scores));
    }
}
