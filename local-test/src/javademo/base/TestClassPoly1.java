package javademo.base;

/**
 * 演示多态特性
 * 演示覆写Object的toString()方法
 * 演示覆写Object的equals()方法
 * 演示覆写Object的hashCode()方法
 */
public class TestClassPoly1 {
	public static void main(String[] args) {
		PolyPerson p1 = new PolyPerson("xiaoming", 25);
		PolyPerson p2 = new PolyPerson("xiaoming", 25);
		PolyPerson p3 = new PolyPerson("xiaogang", 25);
		
		System.out.println(p1.toString());
		
		System.out.println(p1.equals(p2));
		System.out.println(p1.equals(p3));
		
		System.out.println(p1.hashCode());
	}

}


class PolyPerson{
	protected String name;
	protected int age;
	
	public PolyPerson(String name, int age) {
		this.name = name;
		this.age = age;
	}
	
	@Override
	public String toString() {
		return "Person:name=" + name;
	}
	
	//比较是否相等
	@Override
	public boolean equals(Object o) {
		//当且仅当o为PolyPerson类型
		if (o instanceof PolyPerson) {
			PolyPerson p = (PolyPerson) o;
			//且name和age都相等，返回true
			return this.name.equals(p.name)&& this.age==p.age;
		}
		return false;
	}
	
	//计算hash
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
}