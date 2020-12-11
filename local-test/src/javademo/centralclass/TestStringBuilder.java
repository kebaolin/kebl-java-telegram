package javademo.centralclass;


/**
 * Date: Dec 10, 2020
 * Title: 演示StringBuilder的使用
 * Description: StringBuilder的append方法返回this，可以进行链式操作；
 *                       模仿append方法，构造一个计数器；
 * Filename: TestStringBuilder.java
 */
public class TestStringBuilder {
	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder(1024);
		sb.append("aaa").append("**").append("bbb");  //append返回this，可以链式操作
		System.out.println(sb.toString());
		
		//计数器
		Adder as = new Adder();
		as.add(10).add(20).inc().inc(); //链式调用
		as.getValue();
	}
}

//构造一个计数器，可以链式调用自己的方法
class Adder{
	private int sum = 0;
	
	//返回this
	public Adder add(int n) {
		sum += n;
		return this;
	}
	
	//自增并返回this
	public Adder inc() {
		sum++;
		return this;
	}
	
	public void getValue() {
		System.out.println(this.sum);
	}
}