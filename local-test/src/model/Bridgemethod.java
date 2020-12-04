package model;

import java.util.Random;

class Node<T> {
	public T data;
	
	public Node(T data) {this.data = data;}
	
	public void setData(T data) {
		System.out.println("Node.setData");
		this.data = data;
	}
}

class MyNode extends Node<Integer>{
	public MyNode(Integer data) {super(data);}
	
	public void setData(Integer data) {
		System.out.println("MyNode.setData");
		super.setData(data);
	}
	
}

// After type erasure, the Node and MyNode classes becomes：
class Node1 {

    public Object data;

    public Node1(Object data) { this.data = data; }

    public void setData(Object data) {
        System.out.println("Node.setData");
        this.data = data;
    }
}

class MyNode1 extends Node1 {

    public MyNode1(Integer data) { super(data); }

    public void setData(Integer data) {
        System.out.println("MyNode.setData");
        super.setData(data);
    }
}
// therefore,the MyNode setData method does not override the Node setData method.


// To solve this problem and preserve the polymorphism of generic types after type erasure, 
// a Java compiler generates a bridge method to ensure that subtyping works as expected. For the MyNode class, 
// the compiler generates the following bridge method for setData:
class MyNode2 extends Node{
	public MyNode2(Integer data) {super(data);}
	public void setData(Object data) {
		setData((Integer)data);
	}
	public void setData(Integer data) {
		System.out.println("MyNode.setData");
		super.setData(data);
	}
}

public class Bridgemethod {
    public static void main(String[] args) {
    	MyNode mn = new MyNode(5);
    	Node n = mn;            // A raw type - compiler throws an unchecked warning
    	n.setData("Hello");     
    	Integer x = mn.data;    // Causes a ClassCastException to be thrown.
    	
//    	MyNode mn = new MyNode(5);
//    	Node n = (MyNode)mn;         // A raw type - compiler throws an unchecked warning
//    	n.setData("Hello");
//    	Integer x = (String)mn.data; // Causes a ClassCastException to be thrown.
	}
}


// java字节码中与调用相关的5中指令：invokestatic invokespecial invokeinterface invokevirtual invokedynamic
interface 客户 {
  boolean isVIP();
}

class 商户 {
  public double 折后价格(double 原价, 客户 某客户) {
    return 原价 * 0.8d;
  }
}

class 奸商 extends 商户 {
  @Override
  public double 折后价格(double 原价, 客户 某客户) {
    if (某客户.isVIP()) {                         // invokeinterface      
      return 原价 * 价格歧视();                    // invokestatic
    } else {
      return super.折后价格(原价, 某客户);          // invokespecial
    }
  }
  public static double 价格歧视() {
    // 咱们的杀熟算法太粗暴了，应该将客户城市作为随机数生成器的种子。
    return new Random()                          // invokespecial
           .nextDouble()                         // invokevirtual
           + 0.8d;
  }
}


//在奸商.class的常量池中，#16为接口符号引用，指向接口方法"客户.isVIP()"。而#22为非接口符号引用，指向静态方法"奸商.价格歧视()"。
$ javap -v 奸商.class ...
Constant pool:
...
#16 = InterfaceMethodref #27.#29        // 客户.isVIP:()Z
...
#22 = Methodref          #1.#33         // 奸商.价格歧视:()D
...
