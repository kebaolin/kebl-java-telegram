package javademo.base;

/**
 * Title: 演示抽象类
 * Description: 演示定义了抽象方法，类必须为抽象类，抽象类不能被实例化；
 *                       继承自抽象类的子类必须覆写父类的抽象方法;
 *                       实例化的时候尽量引用高层类型；
 * Filename: TestClassAbstract.java
 */
public class TestClassAbstract {
	public static void main(String[] args) {
		AbsPerson p = new AbsStudent();  //实例化尽量引用高层类型
//		AbsPerson p1 = new AbsPerson();  //抽象类不能被实例化
	}
}

//定义了抽象方法，必须定义为抽象类
abstract class AbsPerson{
//	public void run();  //编译失败
	
	public abstract void run(); //抽象方法
}

//子类必须覆写父类的抽象方法
class AbsStudent extends AbsPerson{
    
	//覆写抽象方法
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}