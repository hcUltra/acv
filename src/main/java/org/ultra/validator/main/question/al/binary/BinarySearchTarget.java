package org.ultra.validator.main.question.al.binary;

import org.ultra.validator.annotation.CorrectMethod;
import org.ultra.validator.annotation.Validator;
import org.ultra.validator.annotation.ValidatorMethod;
import org.ultra.validator.process.Active;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author hcUltra
 * @description
 * @date 2024/5/2 10:47
 **/
public class BinarySearchTarget {
    @ValidatorMethod
    public static boolean searchTarget(int[] nums, int target) {
        if (nums == null) {
            return false;
        }

        int l = 0, r = nums.length - 1, mid;
        while (l <= r) {
            mid = l + (r - l) / 2;
            if (nums[mid] == target) {
                return true;
            } else if (nums[mid] < target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }

        return false;
    }


    public static void swap(int[] array, int x, int y) {
        int temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }

    @CorrectMethod
    public static boolean systemBinarySearch(int[] nums, int target) {
        return Arrays.binarySearch(nums, target) > 0;
    }

    public static void main(String[] args) {
        List<String> constrains = new ArrayList<>();
        constrains.add("acv000 order asc");
        constrains.add("1 <= acv000.size <= 100");
        constrains.add("-100 <= acv010.value <= 100");
        constrains.add("-100 <= acv100.value <= 100");
        new Active().activateValidator(constrains);
    }
}
