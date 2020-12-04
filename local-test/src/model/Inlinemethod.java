package model;

public class Inlinemethod {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

// 我们来观测一下单态内联缓存和超多态内联缓存的性能差距。为了消除方法内联的影响，请使用如下的命令
//Run with: java -XX:CompileCommand='dontinline,*.passThroughImmigration' Passenger
abstract class Passenger {
	abstract void passThroughImmigration();
	public static void main(String[] args) {
	 Passenger a = new ChinesePassenger();
	Passenger b = new ForeignerPassenger();
	 long current = System.currentTimeMillis();
	 for (int i = 1; i <= 2_000_000_000; i++) {
	   if (i % 100_000_000 == 0) {
	     long temp = System.currentTimeMillis();
	     System.out.println(temp - current);
	     current = temp;
	   }
	   Passenger c = (i < 1_000_000_000) ? a : b;
	   c.passThroughImmigration();
	}
	}
}
class ChinesePassenger extends Passenger {
@Override void passThroughImmigration() {} 
}
class ForeignerPassenger extends Passenger {
@Override void passThroughImmigration() {}
}
//
//[root@localhost cqq]# javac Passenger.java 
//[root@localhost cqq]# java Passenger
//cost time : 1167
//cost time : 3156
//[root@localhost cqq]# java -XX:CompileCommand='dontinline,*.exit' Passenger
//CompilerOracle: dontinline *.exit
//cost time : 3709
//cost time : 7557