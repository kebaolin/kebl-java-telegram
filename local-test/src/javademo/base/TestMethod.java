package javademo.base;

/**
 * Title: 方法基础概念
 * Description: 演示方法的基本操作及权限隔离
 * Filename: TestMethod.java
 */
public class TestMethod {
	public static void main(String[] args) {
		Person ming = new Person();
//		ming.setAge(27);
//		ming.setName("xiaoming");
//		System.out.println(ming.getAge());
//		System.out.println(ming.getName());
		ming.setBirth(1986);
		System.out.println(ming.getAge(2020));
		
		Group p = new Group();
//		p.setNames(new String[]{"xiaoming", "xiaohong"});
//		p.setNames(null);
		p.setNames("xiaoming", "xiaohong", "xiaogang");
		p.setNames();
	}

}


class Person{
	private String name;
	private int age;
	private int birth;
	
	public void setName(String name) {
		if (name == null || name.isEmpty()) 
			throw new IllegalArgumentException("invalid name");
		this.name = name;
	}
	
	public void setAge(int age) {
		if (age >100 || age < 0)
			throw new IllegalArgumentException("invalid age");
		this.age = age;
	}
	
	public void setBirth(int birth) {
		if(birth >2020 || birth < 1900)
			throw new IllegalArgumentException("invalid birth");
		this.birth = birth;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getAge(int currentYear) {
		return calAge(currentYear);
	}
	
	private int calAge(int currentYear) {
		return currentYear - this.birth;
	}
}

class Group{
	private String[] names;
	
//	public void setNames(String[] names) {
//		this.names = names;
//	}
	
	public void setNames(String... names) {
		this.names = names;
	}
}