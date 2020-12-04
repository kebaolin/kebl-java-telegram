package model;

import java.util.StringJoiner;

public class TestStringJoiner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String[] names = {"job", "kel", "dkdk"};
//		StringJoiner sj = new StringJoiner(", ", "hello ", "!");
//		for (String name: names) {
//			sj.add(name);
//		}
//		System.out.println(sj.toString());
		String[] fields = {"name", "position", "salary"};
		String table = "emplooyee";
		String sql = buildSelectSql(table, fields);
		System.out.println(sql);
	}
	static String buildSelectSql(String table, String[] fields) {
//		StringJoiner sj = new StringJoiner(", ","select ", " from ");
//		for (String field: fields) {
//			sj.add(field);
//		}
//		return sj.toString() + table;
		return String.format("select %s from %s", String.join(", ", fields), table);
	}

}

class PersonOne{
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}

	private String name;
	private int age;
}
