package javademo.base;

/**
 * Title: 构造方法
 * Description: 演示无参、多个参数构造方法以及构造方法复用
 * Filename: TestConsMethod.java
 */
public class TestConsMethods {
	public static void main(String[] args) {
		ConsPerson ming = new ConsPerson("xiaoming", 28);
		System.out.println(ming.getAge());
		System.out.println(ming.getName());
		
		ConsPerson hong = new ConsPerson();
		System.out.println(hong.getAge());
		System.out.println(hong.getName());
		
		ConsPerson gang = new ConsPerson("xiaogang");
		System.out.println(gang.getAge());
		System.out.println(gang.getName());
	}

}


class ConsPerson{
//	private String name; //private不能被继承访问
	protected String name;
	private int age;
	
	public ConsPerson(String name, int age) {
		this.name = name;
		this.age = age;
	}
	
	public ConsPerson() {
		
	}
	
	public ConsPerson(String name) {
		this(name, 18);
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getAge() {
		return this.age;
	}
}