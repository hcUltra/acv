package org.ultra.validator.main.question.al.binary;

import org.ultra.validator.annotation.CorrectMethod;
import org.ultra.validator.annotation.Validator;
import org.ultra.validator.annotation.ValidatorMethod;
import org.ultra.validator.process.Active;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hcUltra
 * @description
 * @date 2024/5/2 12:09
 **/
public class LessAndEqualsNumberMostRightPosition {
    @ValidatorMethod
    public static int lessAndEqualsNumberMostRightPosition(int[] nums, int target) {
        if (nums == null) {
            return -1;
        }

        int l = 0, r = nums.length - 1, mid, ans = -1;
        while (l <= r) {
            mid = l + (r - l) / 2;
            if (nums[mid] <= target) {
                l = mid + 1;
                ans = mid;
            } else {
                r = mid - 1;
            }
        }
        return ans;
    }


    public static void swap(int[] array, int x, int y) {
        int temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }

    @CorrectMethod
    public static int correct(int[] nums, int target) {
        for (int i = nums.length - 1; i >= 0; i--) {
            if (nums[i] <= target) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        List<String> constrains = new ArrayList<>();
        // 数组升序
        constrains.add("acv000 order asc");
        constrains.add("1 <= acv000.size <= 100");
        constrains.add("-100 <= acv010.value <= 100");
        constrains.add("-100 <= acv100.value <= 100");
        new Active().activateValidator(constrains);
    }
}