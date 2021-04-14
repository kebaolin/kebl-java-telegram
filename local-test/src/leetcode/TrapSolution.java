package leetcode;

import java.util.Stack;

/**
 * Date: Apr 6, 2021
 * Title: 接雨水
 * Description: 
 * Filename: TrapSolution.java
 */
public class TrapSolution {

	public static void main(String[] args) {
		int[] height = {0,1,0,2,1,0,1,3,2,1,2,1};
		System.out.println(trap(height));
		System.out.println(dynamicTrap(height));
		System.out.println(doubleIndexTrap(height));
		System.out.println(stackTrap(height));
	}
	// 暴力解法
	private static int trap(int[] height) {
		int size = height.length, ans = 0;
		// 找出每一个位置能接的雨水，头尾元素不用考虑
		// 当前位置能装多少水，取决于当前位置左边的最大值和右边的最大值，两者之中的小者
		for (int i = 0; i < size-1; i++) {
			int leftMax = 0, rightMax = 0;
			// 向左遍历，找出左边最大值
			for (int j = i; j >=0; j--) {
				leftMax = Math.max(height[j], leftMax);
			}
			// 向右遍历，找出右边最大值
			for (int j = i; j < height.length; j++) {
				rightMax = Math.max(height[j], rightMax);
			}
			// 两者的小者，减去当前位置高度，即为能接多少雨水
			ans += Math.min(leftMax, rightMax) - height[i];
		}
		return ans;
	}
	
	// 动态解法，暴力解法每个位置都要遍历一次，动态解法将每个位置左右两边最大值都记录下来
	// leftMax[i] = max(height[i], leftMax[i-1])]
	private static int dynamicTrap(int[] height) {
		int size = height.length, ans = 0;
		// 创建两个数组，分别代表每个元素左右两边的最大值
		int[] leftMax = new int[size];
		int[] rightMax = new int[size];
		// 初始左边最大值为第一个元素（0处）
		leftMax[0] = height[0];
		// 遍历得到每个位置左边最大值
		for (int i = 1; i < size; i++) {
			leftMax[i] = Math.max(height[i], leftMax[i-1]);
		}		
		// 初始右边最大值为最后一个元素（size-1处）
		rightMax[size-1] = height[size-1]; 
		// 遍历得到每个位置右边最大值
		for (int j = size-2; j >= 0; j--) {
			rightMax[j] = Math.max(height[j], rightMax[j+1]);
		}
		
		for (int i = 1; i < size-1; i++) {
			// 当前位置左右两边最大值的小者，减当前位置高度，即为接的雨水的高度
			ans += Math.min(leftMax[i], rightMax[i]) - height[i];
		}
		return ans;
	}
	
	// 双指针法，每个位置能存储的水量取决于他左右两边的最大值中的小者
	// 定义两个指针，从两边往中间移动
	// 核心重点：当height[left] < height[right]的时候，一定有leftMax < rightMax ,反之亦然
	private static int doubleIndexTrap(int[] height) {
		int size = height.length, ans = 0;
		int left = 0, right = size-1;
		int leftMax = 0, rightMax = 0;
		while (left < right) {
			leftMax = Math.max(height[left], leftMax);
			rightMax = Math.max(height[right], rightMax);
			// 左边小于右边，接雨水量取决于左边最大值
			if (height[left] < height[right]) {
				ans += leftMax - height[left];
				left++;
			} else {
				ans += rightMax - height[right];
				right--;
			}
		}
		return ans;
	}
	
	// 单调递减栈解法，当前墙的高度小于栈顶的墙的高度，可以积水，入栈，当前墙的高度大于栈顶的墙的高度，可以结算一次接水量
	// 结算方法为，弹出栈顶元素，新的栈顶元素即为原来数组左边位置，当前墙即为原来数组右边位置，取两者的小者，减去高度（栈顶元素），即为栈顶元素接水量
	// 然后继续弹出栈顶元素，继续结算栈顶元素接水量，需要注意宽度
	private static int stackTrap(int[] height) {
		int ans = 0, cur = 0;
		Stack<Integer> stack = new Stack<Integer>();
		while (cur < height.length) {
			// 如果栈不空且当前墙高度大于栈顶的墙高度，结算一次
			// 重复结算每个新的栈顶，只到栈空或者当前墙小于等于栈顶的墙的高度
			while (!stack.isEmpty() && height[cur] > height[stack.peek()]) {
				// 弹出栈顶元素
				int top = stack.pop();
				// 弹出后为空，不能接水，直接插入新墙即可
				if (stack.isEmpty()) {
					break;
				}
				// 墙之间的宽度，为：当前位置减去新栈顶位置
				int width = cur - stack.peek() - 1;
				// 能接水的高度，为：弹出栈顶后的新栈顶和当前墙的较小者，减去弹出位置的高度
				int bounded_height = Math.min(height[stack.peek()], height[cur]) - height[top];
				ans += bounded_height * width;
			}
			// 结算完毕后，将当前墙入栈
			stack.push(cur);
			cur++;
		}
		return ans;
	}

}

