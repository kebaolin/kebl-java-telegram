package algorithm.sort;

/**
 * Date: Mar 10, 2021
 * Title: 插入排序
 * Description: 插入排序基础版
 * Filename: Insertion.java
 */
public class Insertion {
	//排序
	public void sort(Comparable[] a) {
		//默认第一个已经排序，从第二个开始比较，也就是索引为1的位置，只到数组最后一个
		for (int i = 1; i < a.length; i++) {
			//从当前位置往前比较，倒序遍历索引
			for (int j = i; j > 0; j--) {
				//如果当前位置前一个数字大于当前位置的，则交换位置，否则直接退出循环，当前位置就是正确位置
				if (greater(a[j-1], a[j])) {
					exch(a, j-1, j);
				}
				else {
					break;
				}
			}
		}
	}
	//比较两个对象
	private static boolean greater(Comparable v, Comparable w) {
		return v.compareTo(w) > 0;
	}
	
	//数组元素i和j交换位置
	private static void exch(Comparable[] a, int i, int j) {
		Comparable temp = a[i];
		a[i] = a[j];
		a[j] = temp; 
	}
}
