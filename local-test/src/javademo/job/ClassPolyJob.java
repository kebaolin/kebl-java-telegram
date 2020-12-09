package javademo.job;

public class ClassPolyJob {
	public static void main(String[] args) {
		Income[] incomes = new Income[] {
				new Income(8000),
				new Salary(12000),
				new RoyaltyInocome(25000)
		};
		System.out.println(totalTax(incomes));
	}
	
	public static double totalTax(Income[] incomes) {
		double totalTax = 0;
		for (Income income : incomes) {
			totalTax += income.getTax();
		}
		return totalTax;
	}

}

//基本收入
class Income{
	protected double income;
	
	public Income(double income) {
		this.income = income;
	}
	
	//算税
	public double getTax() {
		return income * 0.1; 
	}
}

//薪水收入
class Salary extends Income{
	public Salary(double income) {
		super(income);
	}
	
	//算税
	@Override
	public double getTax() {
		if (income <= 5000)
			return 0;
		return (income - 5000) * 0.2;
	}
}

//稿费收入
class RoyaltyInocome extends Income{
	public RoyaltyInocome(double income) {
		super(income);
	}
	
	//算税
	@Override
	public double getTax() {
		if (income <= 10000)
			return income * 0.25;
		return income * 0.5;
	}
}