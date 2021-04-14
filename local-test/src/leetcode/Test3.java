package leetcode;

import java.util.Arrays;

/**
 * Date: Mar 15, 2021
 * Title: 
 * Description: 
 * Filename: Test3.java
 */
public class Test3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int[] a = new int[] {10,9,8,7,6};
//		for (int i = 100,j = 0; i >90; i--, j++) {
//			a[j] =i;
//		}
//		
//		for (int i = 1,j = 10; i < 90; i++, j++) {
//			a[j] =i;
//		}
		
		
		int MAX_RUN_COUNT = 67, left=0, right=4;
		int[] run = new int[MAX_RUN_COUNT + 1];
        int count = 0; run[0] = left;

        // Check if the array is nearly sorted
        for (int k = left; k < right; run[count] = k) {
            if (a[k] < a[k + 1]) { // ascending
                while (++k <= right && a[k - 1] <= a[k]);
            } else if (a[k] > a[k + 1]) { // descending
                while (++k <= right && a[k - 1] >= a[k]);
                System.out.println("k is" + k);
                for (int lo = run[count] - 1, hi = k; ++lo < --hi; ) {
                	System.out.println("run is " + run[count]);
                	System.out.println("lo is " + lo);
                    int t = a[lo]; a[lo] = a[hi]; a[hi] = t;
                }
            } 

            /*
             * The array is not highly structured,
             * use Quicksort instead of merge sort.
             */
            count++;
        }
        
        System.out.println(Arrays.toString(a));

	}

}
