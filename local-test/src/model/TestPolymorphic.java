package model;

public class TestPolymorphic {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Income[] incomes = new Income[]{
			new Income(3000),
			new Salary(7500),
        	new StateCouncilSpecialAllowance(15000)
		};
		System.out.println(totalTax(incomes));
	}

	private static double totalTax(Income[] incomes) {
		// TODO Auto-generated method stub
		double totalAmount = 0;
		for(Income income:incomes) {
			totalAmount = totalAmount + income.getTax();
		}
		return totalAmount;
	}

}
/**
 * 
 * @author yehongjuan
 *普通收入
 */
class Income{
	protected double income;
	
	public Income(double income) {
		this.income = income;
	}
	
	public double getTax() {
		return income * 0.1; //税率10%
	}
}

/**
 * 
 * @author yehongjuan
 *工资收入
 */
class Salary extends Income{
	public Salary(double income) {
		super(income);
	}
	
	@Override
	public double getTax() {
		if(income<=5000) {
			return 0;
		}
		return (income-5000) * 0.2; //超过5000的工资，税率20%
	}
}

/**
 * 
 * @author yehongjuan
 *国务院津贴
 */
class StateCouncilSpecialAllowance extends Salary{
	public StateCouncilSpecialAllowance(double income) {
		super(income);
	}
	
	@Override
	public double getTax() {
		return 0; //免税
	}
}