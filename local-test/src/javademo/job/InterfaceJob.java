package javademo.job;

/**
 * 接口作业
 * @author yehongjuan
 *
 */
public class InterfaceJob {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BaseIncome[] incomes = {
				new InSalary(10000),
				new InRoyaltyInocome(25000)
		};
		System.out.println(totalTax(incomes));
	}
	
	public static double totalTax(BaseIncome[] incomes){
		double totalTax = 0;
		for (BaseIncome income : incomes)
			totalTax += income.getTax();
		return totalTax;
	}

}

//定义接口
interface BaseIncome{
	double getTax();
}

//薪水类实现接口
class InSalary implements BaseIncome{
	private double income;
	
	public InSalary(double income) {
		this.income = income;
		
	}
	
	//覆写接口的方法
	@Override
	public double getTax(){
		if (income <= 5000)
			return 0;
		return (income -5000) * 0.2; //S
	}
}

//稿费类实现接口
class InRoyaltyInocome implements BaseIncome{
	private double income;
	
	public InRoyaltyInocome(double income) {
		this.income = income;
	}
	
	//覆写接口的方法
	@Override
	public double getTax() {
		if (income <= 10000)
			return income * 0.25;
		return income * 0.5;
	}
}