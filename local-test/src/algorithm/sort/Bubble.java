package algorithm.sort;

/**
 * Date: Mar 9, 2021
 * Title: 冒泡排序
 * Description: 冒泡排序基础版
 * Filename: Bubble.java
 */
public class Bubble {
	//排序
	public void sort(Comparable[] a) {
		//第一次所有元素参与冒泡，然后每次减少一个
		for (int i = a.length -1; i > 0; i--) {
			//从第一个元素开始两两比较，比较的末尾为所有参与冒泡的数字的前一个，因为是j+1
			for (int j = 0; j < i; j++) {
				//如果当前位置大于后一个数字，则交换位置
				if (greater(a[j], a[j+1])) {
					exch(a, j, j+1);
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
