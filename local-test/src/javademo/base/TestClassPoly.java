package javademo.base;


/**
 * 演示多态特性
 * 定义一个收入基类Income
 * 定义一个薪水类Salary，继承自Income，覆写getTax方法
 * 定义一个国务院津贴类StateCouncilSpecialAllowance，继承自Income，覆写getTax方法
 */
public class TestClassPoly {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Income[] incomes = new Income[] {
				new Income(5000),
				new Salary(8000),
				new StateCouncilSpecialAllowance(10000)
		};
		System.out.println(totalTax(incomes));

	}
	
	public static double totalTax(Income[] incomes) {
		double totalTax = 0;
		//每份收入算税
		for (Income income : incomes) {
			totalTax += income.getTax();
		}
		return totalTax;
	}

}

class Income{
	protected double income;
	
	public Income(double income) {
		this.income = income;
	}
	
	public double getTax() {
		return income * 0.1; //10%
	}
}

class Salary extends Income{
	public Salary(double income) {
		super(income);
	}
	
	@Override
	public double getTax() {
		if (income <= 5000)
			return 0;
		return (income - 5000) * 0.2; //20% 
	}
}

class StateCouncilSpecialAllowance extends Income{
	public StateCouncilSpecialAllowance(double income) {
		super(income);
	}
	
	@Override
	public double getTax() {
		return 0;
	}
}
