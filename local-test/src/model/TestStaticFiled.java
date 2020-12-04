package model;

public class TestStaticFiled {

}

class Person{
	public static String name;
	public static int number;
	
	public static void setName(String value, int pass) {
		name = value;
		number = pass;
	}
}
