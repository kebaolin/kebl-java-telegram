package javademo.job;

/**
 * 抽象类作业
 * @author yehongjuan
 *
 */
public class ClassAbstractJob {
	public static void main(String[] args) {
		AbsIncome[] incomes =  {
				new AbsSalary(10000),
				new AbsRoyaltyInocome(25000)
		};
		System.out.println(totalTax(incomes));
	}
	
	public static double totalTax(AbsIncome[] incomes) {
		double totalTax = 0;
		for (AbsIncome income : incomes) {
			totalTax += income.getTax();
		}
		return totalTax;
	}
}

//定义抽象类
abstract class AbsIncome{
	protected double income;
	
	public AbsIncome(double income) {
		this.income = income;
	}
	
	//抽象方法
	public abstract double getTax();
}

//薪水类
class AbsSalary extends AbsIncome{
	public AbsSalary(double income) {
		super(income);
	}
	
	//覆写抽象方法
	@Override
	public double getTax() {
		if (income <= 5000)
			return 0;
		return (income - 5000) * 0.2;
	}
}

//稿费收入类
class AbsRoyaltyInocome extends AbsIncome{
	public AbsRoyaltyInocome(double income) {
		super(income);
	}
	
	//覆写抽象类
	@Override
	public double getTax() {
		if (income <= 10000)
			return income * 0.25;
		return income * 0.5;
	}
}