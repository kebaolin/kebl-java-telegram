
public class OverLoad extends OverLoadParent{
	
	public String teach(String yuwen,Integer age) {
		
		return super.teach(yuwen, age);
	}
	
	public void teach(String yuwen,Integer age,String addr) {
		
		return;
	}
	

	public static void main(String[] args) {
		System.out.println("---叶柯喆---");
		OverLoad ov = new OverLoad();
		System.out.println(ov.teach("子语文", 5));
		
		OverLoadParent ovp = new OverLoadParent();
		System.out.println(ovp.teach("父语文", 30));

	}

}

class OverLoadParent {
	public String teach(String yuwen,Integer age) {
		
		return yuwen+age;
	}
	
}
