package algorithm.sort;

/**
 * Date: Mar 9, 2021
 * Title: 选择排序
 * Description: 选择排序基础版
 * Filename: Selection.java
 */
public class Selection {
	//排序
	public void sort(Comparable[] a) {
		//默认第一个元素为最小，依次和后面每个数字比较，末尾为数组结尾前一个，因为j=i+1
		for (int i = 0; i < a.length-1; i++) {
			//默认进入时候索引为最小元素索引
			int minIndex = i;
			//用进入时候当前元素，依次和后面每个元素比较，比较末尾为数组最后一个元素
			for (int j = i+1; j < a.length; j++) {
				//如果进入时候元素比后面元素大，则交换最小元素索引为后面元素位置，然后从最小位置继续比较，只到找出真实到最小元素索引
				if (greater(a[minIndex], a[j])) {
					minIndex = j;
				}
			}
			exch(a, i, minIndex);
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
