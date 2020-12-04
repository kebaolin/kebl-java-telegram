package model;

public class TestStringBuilder {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		StringBuilder sb = new StringBuilder(1024);
//		sb.append("mr").append("Bob").append("!").insert(0, "hello");
//		System.out.println(sb.toString());
//		Adder adder = new Adder();
//		adder.add(10).add(8).inc().add(18);
//		System.out.println(adder.value());
		String[] fields = {"name", "position", "salary"};
		String table = "employee";
		String insert = buildInsertSql(table, fields);
		System.out.println(insert);
		String s = "insert into employee (name, position, salary) values (?, ?, ?)";
        System.out.println(s.equals(insert) ? "测试成功" : "测试失败");
	}
	 static String buildInsertSql(String table, String[] fields) {
	     // TODO:
		 StringBuilder sb = new StringBuilder(1024);
		 sb.append("insert into ").append(table).append(" (");
		 for (String field: fields) {
			 if (field.equals(fields[fields.length-1])) {
				 sb.append(field);
			 } else {
				 sb.append(field).append(", ");
			 }
		 }
		 sb.append(") ").append("values (?, ?, ?)");
	     return sb.toString();
	 }

}

 class Adder {
	 private int sum = 0;
	 
	 public Adder add(int n) {
		 sum += n;
		 return this;
	 }
	 
	 public Adder inc() {
		 sum++;
		 return this;
	 }
	 
	 public int value() {
		 return sum;
	 }
 }