package org.ultra.validator.main.question.al.sort;

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
 * @date 2024/5/2 10:12
 **/
public class InsertSort {
    @ValidatorMethod
    public static int[] insertSort(int[] array) {
        if (array == null || array.length < 2) {
            return array;
        }

        for (int i = 1; i < array.length; i++) {
            for (int j = i - 1; j >= 0 && array[j] > array[j + 1]; j--) {
                swap(array, j, j + 1);
            }
        }
        return array;
    }


    public static void swap(int[] array, int x, int y) {
        int temp = array[x];
        array[x] = array[y];
        array[y] = temp;
    }

    @CorrectMethod
    public static int[] systemSort(int[] array) {
        Arrays.sort(array);
        return array;
    }

    public static void main(String[] args) {
        List<String> constrains = new ArrayList<>();
        constrains.add("2 <= acv000.size <= 10000");
        constrains.add("-1000000000 <= acv010.value <= 1000000000");
        new Active().activateValidator(constrains);
    }
}
