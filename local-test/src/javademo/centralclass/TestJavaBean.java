package javademo.centralclass;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

/**
 * Date: Dec 11, 2020
 * Title: 演示javaBean
 * Description: 演示javaBean的命名规范，字段名为xyz，方法名为get以及set开头，后跟Xyz；
 *                       以及自动创建getter和setter；
 *                       通过核心库的Introspector来枚举一个javaBean的所有属性；
 * Filename: TestJavaBean.java
 */
public class TestJavaBean {
	public static void main(String[] args) throws IntrospectionException {
		BeanInfo ba = Introspector.getBeanInfo(JbPerson.class);
		for (PropertyDescriptor pd : ba.getPropertyDescriptors()) {
			System.out.println(pd.getName());  //字段名
			System.out.println(pd.getReadMethod());
			System.out.println(pd.getWriteMethod());
		}
	}
}


class JbPerson{
	private String name;
	private int age;
	
	//自动生成
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}	
}