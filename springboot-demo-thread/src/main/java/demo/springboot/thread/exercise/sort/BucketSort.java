/**
 * LY.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package demo.springboot.thread.exercise.sort;

/**
 * 
 * @author bilahepan
 * @version $Id: BuckSort.java, v 0.1 2017年10月23日 下午11:37:57 bilahepan Exp $
 */
public class BucketSort {
    public static void main(String[] args) {
        int[] nums = { 1, 4, 2, 3, 2, 22, 44, 77, 33, 99 };
        int[] rsult = sort(nums, 100);
        for (int i = 0; i < nums.length; i++) {
            System.out.println(rsult[i]);
        }
    }

    /**
     * 
     * @param nums
     * @param maxSize
     * @return
     */
    public static int[] sort(int[] nums, int maxSize) {
        int[] sorted = new int[maxSize];
        int[] result = new int[maxSize];
        for (int i = 0; i < nums.length; i++) {
            sorted[nums[i]]++;//计数
        }
        int flag = 0;
        for (int i = 0; i < maxSize; i++) {
            for (int j = 0; j < sorted[i]; j++) {
                result[flag] = i;//结果盛放
                flag++;
            }
        }
        return result;
    }
}
