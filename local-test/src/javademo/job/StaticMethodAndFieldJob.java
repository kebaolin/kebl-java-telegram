package javademo.job;

/**
 * Date: Dec 10, 2020
 * Title: 静态变量和静态方法作业
 * Description: 练习
 * Filename: StaticMethodAndFieldJob.java
 */
public class StaticMethodAndFieldJob {
	public static void main(String[] args) {
		StPerson p = new StPerson("xiaoming");
		StPerson p1 = new StPerson("xiaogang");
		System.out.println(StPerson.getCount());
		StPerson p2 = new StPerson("xiaohong");
		System.out.println(StPerson.getCount());
	}

}

class StPerson{
	private String name;
	public static int count = 0;
	
	public StPerson(String name) {
		this.name = name;
		count++;
	}
	
	public String getName() {
		return this.name;
	}
	
	public static int getCount() {
		return count;
	}
}