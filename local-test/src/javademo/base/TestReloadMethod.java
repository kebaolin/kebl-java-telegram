package javademo.base;


/**
 * Title: 方法重载
 * Description: 演示方法重载，相同的返回类型，不同的参数
 * FileName: TestReloadMethod.java
 */
public class TestReloadMethod {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReloadPerson ming = new ReloadPerson();
		ReloadPerson hong = new ReloadPerson();
		ming.setName("xiaoming");
		hong.setName("xiao", "hong");
        System.out.println(ming.getName());
        System.out.println(hong.getName());
	}

}

class ReloadPerson {
	private String name;
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setName(String firstName, String secondName) {
		this.name = firstName + " " + secondName;
	}
}
