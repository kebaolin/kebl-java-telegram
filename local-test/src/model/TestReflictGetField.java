package model;

public class TestReflictGetField {

	public static void main(String[] args) throws NoSuchFieldException, SecurityException {
		// TODO Auto-generated method stub
		Class stdClass = Students.class;
		System.out.println(stdClass.getField("score"));
        System.out.println(stdClass.getField("name")); 
        System.out.println(stdClass.getDeclaredField("grade"));
	}
	
	class Person{
		public String name;
	}
	
	class Students extends Person{
		public int score;
		private int grade;
	}

}
