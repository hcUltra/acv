package org.ultra.validator.main;

import org.ultra.validator.annotation.CorrectMethod;
import org.ultra.validator.annotation.Validator;
import org.ultra.validator.annotation.ValidatorMethod;
import org.ultra.validator.config.ArgumentsConfig;
import org.ultra.validator.process.Active;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public void start() {
        List<String> constrains = new ArrayList<>();
//        constrains.add("acv000 order desc");

        constrains.add("2 <= acv000.size <= 10000");
        constrains.add("-1000000000 <= acv010.value <= 1000000000");
        constrains.add("-1000000000 <= acv100.value <= 1000000000");

        new Active().activateValidator(constrains);
    }

    public static void main(String[] args) {
        new Solution().start();
    }
}