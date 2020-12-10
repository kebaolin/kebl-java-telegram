package javademo.base;

/**
 * Date: Dec 10, 2020
 * Title: 演示静态方法和静态变量
 * Description: 演示静态变量通过变量访问（不推荐）及通过类名访问；
 *                       演示静态方法通过变量访问（不推荐）及通过类名访问，静态方法不能访问this变量以及实例字段；
 *                       演示接口创建静态字段，必须为public static final修饰，可省略
 * Filename: TestStatciMethodAndField.java
 */
public class TestStatciMethodAndField {
	public static void main(String[] args) {
		StPerson p = new StPerson();
		p.setName("xiaogang");
		System.out.println(p.name); //不推荐使用变量访问静态字段，只是编译器自动转变的，会有警告
		
		System.out.println(StPerson.name); //使用类名.静态字段进行访问
		
		StPerson p1 = new StPerson();
		p1.setName("xiaohong");  //任何实例修改静态字段，都是相同效果，所有实例的静态字段都被修改
		System.out.println(StPerson.name);  //xiaohong
		
		StPerson.name = "xiao ming";  //类名.静态字段直接赋值
		System.out.println(StPerson.name); //xiaoming
		
		p.setAge(28); //不推荐使用变量访问静态方法，只是编译器自动转变的，会有警告
		System.out.println(StPerson.age); 
		
		StPerson.setAge(30); //类名.静态方法直接访问
		System.out.println(StPerson.age);
	}

}

class StPerson{
	public static String name; //创建静态字段name
	public static int age;
	public int grade;
	
	public void setName(String value) {
		name = value;
	}
	
	//静态方法
	public static void setAge(int number) {
		age = number;
//		this.grade = number; //不能使用this变量
//		grade = number; //不能使用实例字段
	}
}