package algorithm.sort;

/**
 * Date: Mar 10, 2021
 * Title: 希尔排序
 * Description: 希尔排序
 * Filename: Shell.java
 */
public class Shell {
	//比较
	public void sort(Comparable[] a) {
		int gap = 1;
		//求增量因子
		while (gap < a.length/2) {
			gap = gap * 2 + 1;
		}
		//当增量因子为1，执行最后一次插入排序
		while (gap >= 1) {
			//默认分组第一个元素都是有序的，从gap处进入为第一个待插入元素，依次往后，只到数组末尾
			for (int i = gap; i < a.length; i++) {
				//当前位置为待插入元素，插入到前面已排序的序列中，相隔为gap
				for (int j = i; j >=gap; j-=gap) {
					if (greater(a[j-gap], a[j])) {
						exch(a, j-gap, j);
					}
					else {
						break;
					}
				}
			}
			gap = gap / 2;
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
