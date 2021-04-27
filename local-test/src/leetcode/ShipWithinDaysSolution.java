package leetcode;

import java.util.Arrays;

/**
 * Date: Apr 26, 2021
 * Title: 在 D 天内送达包裹的能力
 * Description: 传送带上的包裹必须在 D 天内从一个港口运送到另一个港口。

传送带上的第 i 个包裹的重量为 weights[i]。每一天，我们都会按给出重量的顺序往传送带上装载包裹。我们装载的重量不会超过船的最大运载重量。

返回能在 D 天内将传送带上的所有包裹送达的船的最低运载能力

示例 1：

输入：weights = [1,2,3,4,5,6,7,8,9,10], D = 5
输出：15
解释：
船舶最低载重 15 就能够在 5 天内送达所有包裹，如下所示：
第 1 天：1, 2, 3, 4, 5
第 2 天：6, 7
第 3 天：8
第 4 天：9
第 5 天：10

请注意，货物必须按照给定的顺序装运，因此使用载重能力为 14 的船舶并将包装分成 (2, 3, 4, 5), (1, 6, 7), (8), (9), (10) 是不允许的。 

 * Filename: ShipWithinDaysSolution.java
 */
public class ShipWithinDaysSolution {
	public static void main(String[] args) {
		int[] weights = {10,50,100,100,50,100,100,100};
		int D = 5;
		System.out.println(shipWithinDays(weights, D));
	}
	
	// D天类送达，采用二分查找，先确定查找范围
	// 最小载重必须大于等于所有重量中的最大值，要不然有货物永远装不下，且小于所有重量的总值，即范围为(max[weights], sum[weights])
	private static int shipWithinDays(int[] weights, int D) {
		int left = Arrays.stream(weights).max().getAsInt(), right = Arrays.stream(weights).sum();
		while (left <right) {
			int mid = (left + right) / 2;
			// 判定此重量下，完成的天数
			// 初始天数为1，因为最少也需要1天，初始前一天运送的总重量为0
			int need = 1, cur =0;
			for (int weight : weights) {
				cur += weight;
				// 如果加上今天的超过了载重，说明今天的要放到下一天运送了，我们把需要的天数+1
				if (cur > mid) {
					need++;
					// 今天已经开始扫描，把今天重量设置为初始总重量，后一个直接加上即可
					cur = weight;
				}
			}
			System.out.println("mid," + mid + "need," + need );
			// 根据天数比较，来增大载重量
			if (need <= D) {
				// 类似于二分查找里面有重复元素，查找元素第一次出现的位置
				right = mid;
			} else { //说明载重量小了需要天数就多了
				left = mid + 1; //左边重量都抛弃了
			}
		}
		return left;
	}
}
