package javademo.base;

/**
 * Title: 类的继承
 * Description: 演示类的继承，包括权限、构造方法、向上及向下转型
 * Filename: TestClassImp.java
 */
public class TestClassImp {
	public static void main(String[] args) {
		Student s = new Student("xiao ming", 28, 98);
		System.out.println(s.name);
		
		ConsPerson p1 = new Student("xiao hong", 29, 87); //向上转型，ok
		
		ConsPerson p2 = new ConsPerson("xiao gang", 29);
		Student s2 = (Student) p1; //向下转型，ok，因为p1确实是指向的Student实例
//		Student s3 = (Student) p2; //向下转型，编译错误ClassCastException，因为p2指向的是ConsPerson实例
		
		System.out.println(p1 instanceof ConsPerson);
		System.out.println(p1 instanceof Student);
		
		System.out.println(p2 instanceof ConsPerson);
		System.out.println(p2 instanceof Student);
		
		//instanceof判断
		Object obj = "hello";
		if (obj instanceof String) {
			String ss = (String) obj;
			System.out.println(ss.toUpperCase());
		}
		
		//简略写法,java14
//		if (obj instanceof String sss)
//			System.out.println(sss.toUpperCase());
	}

}


class Student extends ConsPerson{
	protected int score;
	
	public Student(String name, int age, int score) {
		super(name, age); //显示调用，否则name，age不能取到变量值
		this.score = score;
	}
}