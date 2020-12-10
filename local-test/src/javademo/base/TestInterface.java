package javademo.base;

/**
 * Title: 演示接口
 * Description: 演示接口定义，子类实现接口，接口定义default方法
 * Filename: TestInterface.java
 */
public class TestInterface {
	public static void main(String[] args) {
		InPerson p = new InStudent("xiaogang");
		p.run();
		System.out.println(p.getName());
	}

}

//定义接口
interface InPerson{
	void run();
	String getName();
	
	//定义default方法
	default int getAge() {
		return 20;
	}
}

//实现接口
class InStudent implements InPerson{
	private String name;
	
	public InStudent(String name) {
		this.name = name;
	}
	
	//覆写run方法
	@Override
	public void run() {
		System.out.println(this.name + "run");
	}
	
	//覆写getName方法
	@Override
	public String getName() {
		return this.name;
	}
	
	//子类可以不必覆写default方法
}