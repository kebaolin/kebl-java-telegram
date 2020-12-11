package javademo.job;

/**
 * Date: Dec 11, 2020
 * Title: stringBuilder作业
 * Description: 练习作业
 * Filename: StringBuilderJob.java
 */
public class StringBuilderJob {
	public static void main(String[] args) {
		String[] fields = { "name", "position", "salary" };
        String table = "employee";
        String insert = buildInsertSql(table, fields);
        System.out.println(insert);
        String s = "INSERT INTO employee (name, position, salary) VALUES (?, ?, ?)";
        System.out.println(s.equals(insert) ? "测试成功" : "测试失败");
	}
	
	static String buildInsertSql(String table, String[] fields) {
		StringBuilder sb = new StringBuilder(1024);
		StringBuilder sb1 = new StringBuilder(1024);
		sb.append("INSERT INTO ").append(table).append(" (");
		for (int i=0;i < fields.length;i++) {
			if (i == fields.length-1) {
				sb.append(fields[i]).append(") VALUES (");
				sb1.append("?)");
			}else {
				sb.append(fields[i]).append(", ");
				sb1.append("?, ");
			}
		}
		sb.append(sb1);
		return sb.toString();
	}

}
