package org.ultra.validator.main;

import org.ultra.validator.annotation.CorrectMethod;
import org.ultra.validator.annotation.Validator;
import org.ultra.validator.annotation.ValidatorMethod;
import org.ultra.validator.config.ArgumentsConfig;
import org.ultra.validator.process.Active;

import java.util.*;

/**
 * 请编写对数器测试代码，请遵循一下规则，并且您可以参考如下 leetcode 第一题的对数器代码，
 * 1. 请不要修改类名
 * 2. @Validator(count=?)   注解用于标注测试次数
 * 3. @ValidatorMethod      注解用于标注对数器方法
 * 4. @CorrectMethod        注解用于标注正确方法
 * 5. 请构造测试配置类 ArgumentsConfig 的对象，对于构造规则详见介绍文档
 * 6. 启动测试 new Active().activateValidator(configs);
 **/
@Validator(count = 1234)
public class Solution {
    /**
     * 示例：
     * O(n) 时间复杂度 对数器方法
     * <p>
     * //     * @param nums   数组
     *
     * @param target 目标数字
     * @return 一个有序数对
     */
    @ValidatorMethod
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int key = target - nums[i];
            if (map.containsKey(key)) {
                return new int[]{map.get(key), i};
            } else {
                map.put(nums[i], i);
            }
        }
        return new int[]{-1, -1};
    }

    /**
     * O(n^2) 时间复杂度 正确方法 暴力破解
     * <p>
     * //     * @param nums   数组
     *
     * @param target 目标数字
     * @return 一个有序数对
     */
    @CorrectMethod
    public int[] twoSumCorrect(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    public double findMedianSortedArraysValidator(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) {
            return findMedianSortedArraysValidator(nums2, nums1);
        }

        int m = nums1.length;
        int n = nums2.length;
        int left = 0, right = m;
        // median1：前一部分的最大值
        // median2：后一部分的最小值
        int median1 = 0, median2 = 0;

        while (left <= right) {
            // 前一部分包含 nums1[0 .. i-1] 和 nums2[0 .. j-1]
            // 后一部分包含 nums1[i .. m-1] 和 nums2[j .. n-1]
            int i = (left + right) / 2;
            int j = (m + n + 1) / 2 - i;

            // nums_im1, nums_i, nums_jm1, nums_j 分别表示 nums1[i-1], nums1[i], nums2[j-1], nums2[j]
            int nums_im1 = (i == 0 ? Integer.MIN_VALUE : nums1[i - 1]);
            int nums_i = (i == m ? Integer.MAX_VALUE : nums1[i]);
            int nums_jm1 = (j == 0 ? Integer.MIN_VALUE : nums2[j - 1]);
            int nums_j = (j == n ? Integer.MAX_VALUE : nums2[j]);

            if (nums_im1 <= nums_j) {
                median1 = Math.max(nums_im1, nums_jm1);
                median2 = Math.min(nums_i, nums_j);
                left = i + 1;
            } else {
                right = i - 1;
            }
        }

        return (m + n) % 2 == 0 ? (median1 + median2) / 2.0 : median1;
    }

    public double findMedianSortedArraysCorrect(int[] nums1, int[] nums2) {
        int length1 = nums1.length, length2 = nums2.length;
        int totalLength = length1 + length2;
        if (totalLength % 2 == 1) {
            int midIndex = totalLength / 2;
            double median = getKthElement(nums1, nums2, midIndex + 1);
            return median;
        } else {
            int midIndex1 = totalLength / 2 - 1, midIndex2 = totalLength / 2;
            double median = (getKthElement(nums1, nums2, midIndex1 + 1) + getKthElement(nums1, nums2, midIndex2 + 1)) / 2.0;
            return median;
        }
    }

    public int getKthElement(int[] nums1, int[] nums2, int k) {
        int length1 = nums1.length, length2 = nums2.length;
        int index1 = 0, index2 = 0;
        int kthElement = 0;

        while (true) {
            // 边界情况
            if (index1 == length1) {
                return nums2[index2 + k - 1];
            }
            if (index2 == length2) {
                return nums1[index1 + k - 1];
            }
            if (k == 1) {
                return Math.min(nums1[index1], nums2[index2]);
            }

            // 正常情况
            int half = k / 2;
            int newIndex1 = Math.min(index1 + half, length1) - 1;
            int newIndex2 = Math.min(index2 + half, length2) - 1;
            int pivot1 = nums1[newIndex1], pivot2 = nums2[newIndex2];
            if (pivot1 <= pivot2) {
                k -= (newIndex1 - index1 + 1);
                index1 = newIndex1 + 1;
            } else {
                k -= (newIndex2 - index2 + 1);
                index2 = newIndex2 + 1;
            }
        }
    }

    public void start() {
        // leetcode1
        List<String> constrains = new ArrayList<>();
        constrains.add("2 <= acv000.size <= 10000");
        constrains.add("-1000000000 <= acv010.value <= 1000000000");
        constrains.add("-1000000000 <= acv100.value <= 1000000000");

        // leetcode4
//        List<String> constrains = new ArrayList<>();
//        constrains.add("500 <= acv000.size <= 1500");
//        constrains.add("500 <= acv100.size <= 1500");
//        constrains.add("(1 <= (acv000.size + acv100.size)) && ((acv000.size + acv100.size) <= 2000)");
//        constrains.add("-10000000 <= acv010.value <= 10000000");
//        constrains.add("-10000000 <= acv110.value <= 10000000");

        ArgumentsConfig configs = new ArgumentsConfig(constrains);
        new Active().activateValidator(configs);
    }

    public static void main(String[] args) {
        new Solution().start();
    }
}