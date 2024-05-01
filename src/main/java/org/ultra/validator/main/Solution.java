package org.ultra.validator.main;

import org.ultra.validator.annotation.CorrectMethod;
import org.ultra.validator.annotation.Validator;
import org.ultra.validator.annotation.ValidatorMethod;
import org.ultra.validator.config.ArgumentConfig;
import org.ultra.validator.config.ArgumentsConfig;
import org.ultra.validator.process.Active;
import org.ultra.validator.range.Range;

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
     *
//     * @param nums   数组
     * @param target 目标数字
     * @return 一个有序数对
     */
    @ValidatorMethod
    public int[] twoSum(HashMap<HashMap<ArrayList[],Integer>,HashMap<Integer,Integer>>target,HashMap<HashMap<Integer,Integer>,HashMap<Integer,Integer>>target2){
//        Map<Integer, Integer> map = new HashMap<>();
//        for (int i = 0; i < nums.length; i++) {
//            int key = target - nums[i];
//            if (map.containsKey(key))
//                return new int[]{map.get(key), i};
//            } else {
//                map.put(nums[i], i);
//            }
//        }
        return new int[]{-1, -1};
    }

    /**
     * O(n^2) 时间复杂度 正确方法 暴力破解
     *
//     * @param nums   数组
     * @param target 目标数字
     * @return 一个有序数对
     */
    @CorrectMethod
    public int[] twoSumCorrect(HashMap<HashMap<ArrayList[],Integer>,HashMap<Integer,Integer>>target,HashMap<HashMap<Integer,Integer>,HashMap<Integer,Integer>>target2){
//        for (int i = 0; i < nums.length; i++) {
//            for (int j = i + 1; j < nums.length; j++) {
//                if (nums[i] + nums[j] == target) {
//                    return new int[]{i, j};
//                }
//            }
//        }
        return new int[]{-1, -1};
    }

    public void start() {
//        // -10^9 <= nums[i] <= 10^9
//        ArgumentConfig innerIntConfig = new ArgumentConfig()
//                .withValueRange(new Range(-Math.pow(10, 9), Math.pow(10, 9)));
//
//        // 2 <= nums.length <= 10^4
//        ArgumentConfig numsConfig = new ArgumentConfig()
//                .withSize(new Range(2, Math.pow(10, 4)))
//                .withInnerConfig(new ArgumentConfig[]{innerIntConfig});
//
//        // -10^9 <= target <= 10^9
//        ArgumentConfig targetConfig = new ArgumentConfig()
//                .withValueRange(new Range(-Math.pow(10, 9), Math.pow(10, 9)));

        List<ArgumentConfig> lpc = new ArrayList<>();
//        lpc.add(numsConfig);
//        lpc.add(targetConfig);
        ArgumentsConfig configs = new ArgumentsConfig(lpc, null);
        new Active().activateValidator(configs);
    }

    public static void main(String[] args) {
        new Solution().start();
    }
}