package leetcode;

/**
 * Date: Apr 27, 2021
 * Title: 寻找两个正序数组的中位数
 * Description: 给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。

 * Filename: FindMedianSortedArraysSolution.java
 */
public class FindMedianSortedArraysSolution {

	public static void main(String[] args) {
		int[] nums1 = {1,2,3,4,10,11,12,14};
		int[] nums2 = {5,6,7,8,9,16,17,18};
		System.out.println(findMedianSortedArraysUseGB(nums1, nums2));
		System.out.println(findMedianSortedArraysUseIndex(nums1, nums2));
		int k = (nums1.length + nums2.length) / 2;
		if ((nums1.length + nums2.length) % 2 == 0){
			System.out.println(findMedianSortedArraysUseEF(nums1, nums2, 8));
			System.out.println(findMedianSortedArraysUseEF(nums1, nums2, 9));
			System.out.println((findMedianSortedArraysUseEF(nums1, nums2, k) + findMedianSortedArraysUseEF(nums1, nums2, k+1)) / 2.0);
		} else {		
			System.out.println(findMedianSortedArraysUseEF(nums1, nums2, k));
		}
	}
	
	// 使用归并思想，两个数组是有序的，所以我们可以按归并思想将两个数组合二为1
	private static double findMedianSortedArraysUseGB(int[] nums1, int[] nums2) {
		// 创建一个数组，用来存放结果
		int[] results = new int[nums1.length + nums2.length];
		// 结果的指针
		int resultIndex = 0;
		// 创建两个指针，分别代表两个数组得起始位置
		int index1 = 0, index2 = 0;
		// 两个数组都不为空，就继续比较，合并，其中一个遍历完成，则退出，将剩余未遍历完成的加入到结果集中即可
		while (index1 < nums1.length && index2 < nums2.length) {
			// 谁小就先把谁放入结果集，同时指针往后移动一位
			if (nums1[index1] <= nums2[index2]) {
//				results[resultIndex] = nums1[index1];
//				index1++;
//				resultIndex++;
				results[resultIndex++] = nums1[index1++];
			} else {
//				results[resultIndex] = nums2[index2];
//				index2++;
//				resultIndex++;
				results[resultIndex++] = nums2[index2++];
			}
		}
		// 一个为空就退出，将剩余的一个加入到结果集中
		while (index1 < nums1.length) {
			results[resultIndex++] = nums1[index1++];
		}
		while (index2 < nums2.length) {
			results[resultIndex++] = nums2[index2++];
		}
		// 判断得到到结果长度是基数还是偶数
		// 偶数返回中间及中间前一个数的和除以2，基数返回中间那个数
		return results.length % 2 ==0 ? (results[results.length/2] + results[results.length/2 - 1]) / 2.0 : results[results.length/2];
	}
	
	/*
	 *  使用双指针来解决
	 *  合并后不管长度是偶数还是奇数，得到结果所需的遍历个数为length/2 + 1次，所以我们限定只查找length/2 + 1次，就一定能得到中间那个元素
	 *  如果总长是奇数，结果就是中间那个数，如果总长是偶数，结果是中间那个数和前面一个数相加除以2，所以我们需要额外用一个变量来保存中间数的前一个数字
	 */
	private static double findMedianSortedArraysUseIndex(int[] A, int[] B) {
		int l1 = A.length, l2 = B.length;
		int len = l1 + l2;
		// 设置指针初始位置
		int aStart = 0, bStart = 0;
		// 设置起始值，right为中间值
		// 长度为奇数我们只需要right，为偶数需要left+right
		int left = -1, right = -1;
		// 遍历次数为len/2+1
		for (int i = 0; i <= len/2; i++) {
			// 每次进入，我们把right的值赋给left，然后寻找right
			left = right;
			// 开始判断，如果第一个数组当前位置的值，小于第二个的值；或者第二个数组已经取完了
			if (aStart < l1 && (A[aStart] < B[bStart] || bStart >= l2)) {
				right = A[aStart++];
			} else {
				right = B[bStart++];
			}
		}
		return len % 2 == 0 ? (left+right) / 2.0 : right;
	}
	
	/*
	 * 采用二分查找方法来解，转换思路为求解第k小的数字，k为中间元素索引
	 * 每次去除一半的无用元素，两个数组分别取k的一半，比较分割后的末尾元素大小，小的一组全部舍弃，然后将k减去舍弃的元素个数
	 */
	private static int findMedianSortedArraysUseEF(int[] nums1, int[] nums2, int k) {
		int len1 = nums1.length, len2 = nums2.length;
		// 定义两个指针
		int index1 = 0, index2 = 0;
		while (true) {
			/*
			 * 如果第一个数组的所有元素已经遍历完都不是第k小的，则从第二个数组返回
			 * 此时index1的在m处，即遍历完数组又向右移动了一位，即退出条件为index1==m
			 * 此时直接从第二个数组取第k个数字，注意此时的第k小的数已经是重新计算过的，即排除掉了一部分
			 */
			if (index1 == len1) {
				return nums2[index2 + k -1];
			}
			// 同理对第二个数组
			if (index2 == len2) {
				return nums1[index1 + k - 1];
			}
			// 如果循环到找第1小的元素的时候，就可以返回结果了，此时结果为剩余数组的第一个元素中的小者
			if (k == 1) {
				return Math.min(nums1[index1], nums2[index2]);
			}
			// 获取分组后新指针的位置，注意数组越界，最大只能到数组的右边界
			int new_index1 = Math.min(index1 + k/2 - 1, len1 - 1);
			int new_index2 = Math.min(index2 + k/2 - 1, len2 - 1);
			// 将两个分组的末尾值进行比较，小的整个部分都舍弃，因为他们不可能为第k小
			// 同时将k的值进行缩小，因为去掉了部分值
			if (nums1[new_index1] < nums2[new_index2]) {
				k -= new_index1 - index1 + 1;
				index1 = new_index1 + 1;
			} else {
				k -= new_index2 - index2 + 1;
				index2 = new_index2 + 1;
			}
		}
	}
}
