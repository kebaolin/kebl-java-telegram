package model;

public class TestRefilct {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		System.out.println(String.class);
		printClassInfo("".getClass());
		printClassInfo(Runnable.class);
		printClassInfo(java.time.Month.class);
		printClassInfo(String[].class);
		printClassInfo(int.class);
	}
	
	static void printClassInfo(@SuppressWarnings("rawtypes") Class cls) {
		System.out.println("Class Name: " + cls.getName());
		System.out.println("Class Simple Name: " + cls.getSimpleName());
		if(cls.getPackage() != null) {
			System.out.println("Class Package Name: " + cls.getPackage().getName());
		}
		System.out.println("is interface: " + cls.isInterface());
		System.out.println("is enum: " + cls.isEnum());
		System.out.println("is Array: " + cls.isArray());
		System.out.println("is primitive: " + cls.isPrimitive());
	}

}
