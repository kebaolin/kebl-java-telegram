package javademo.base;

//构造方法
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
	private String name;
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