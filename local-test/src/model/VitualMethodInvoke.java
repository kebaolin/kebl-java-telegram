package model;

public class VitualMethodInvoke {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

        new ForeignerPassenger().passThroughImmigration();

	}

}


abstract class Passenger {
  abstract void passThroughImmigration();
  @Override
  public String toString() {return "";}
}
class ForeignerPassenger extends Passenger {
   @Override
   void passThroughImmigration() { /* 进外国人通道 */ }
}
class ChinesePassenger extends Passenger {
  @Override
  void passThroughImmigration() { /* 进中国人通道 */ }
  void visitDutyFreeShops() { /* 逛免税店 */ }
}

