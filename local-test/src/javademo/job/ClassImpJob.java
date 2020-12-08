package javademo.job;


/**
 * 继承练习作业
 * @author yehongjuan
 *
 */
public class ClassImpJob {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Person p = new Person("小明", 12);
        Student s = new Student("小红", 20, 99);
        Student ps = new PrimaryStudent("小军", 9, 100, 5);
        System.out.println(ps.getScore()); //100

	}

}

class Person{
	protected String name;
	protected int age;
	
	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public int getAge() {
		return this.age;
	}
}

class Student extends Person{
	protected int score;
	
	public Student(String name, int age, int score) {
		super(name, age);
		this.score = score;
	}
	
	public int getScore() {
		return this.score;
	}
}

class PrimaryStudent extends Student{
	protected int grade;
	
	public PrimaryStudent(String name, int age, int score, int grade) {
		super(name, age, score);
		this.grade = grade;
	}
}
