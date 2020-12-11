package javademo.job;

import java.util.StringJoiner;

/**
 * Date: Dec 11, 2020
 * Title: StringJoiner练习作业
 * Description: 练习使用StringJoiner构造sql查询语句
 * Filename: StringJoinerJob.java
 */
public class StringJoinerJob {
	public static void main(String[] args) {
		String[] fields = { "name", "position", "salary" };
        String table = "employee";
        String select = buildSelectSql(table, fields);
        System.out.println(select);
        System.out.println("SELECT name, position, salary FROM employee".equals(select) ? "测试成功" : "测试失败");
	}
	
	static String buildSelectSql(String table, String[] fields) {
		StringJoiner sj = new StringJoiner(", ", "SELECT ", " FROM " + table);
		for (String field : fields) {
			sj.add(field);
		}
		return sj.toString();
	}

}
