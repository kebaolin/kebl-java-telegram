package model;

public class TestInterface {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

interface Person{
	void run();
	String getName();
}

class Students implements Person{
	private String name;
	public Students(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	@Override
	public void run() {
		System.out.println(this.name + "run");
	}
}