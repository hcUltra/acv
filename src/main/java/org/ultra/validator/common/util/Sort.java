package org.ultra.validator.common.util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author hcUltra
 * @description 对任意数组，根据比较器 comparator 进行排序
 * @date 2024/4/24 08:03
 **/
public class Sort {
    /**
     * TODO 后续再提供一个归并排序 - 稳定排序
     */
    private static int first = 0;
    public static int last = 0;
    public static void sort(Object arr, Comparator comparator) {
        first = last = 0;
        int len = Array.getLength(arr);
        quickSort(arr, 0, len - 1, comparator);
    }

    public static void quickSort(Object array, int l, int r, Comparator comparator) {
        if (l >= r) {
            return;
        }
        Object x = Array.get(array, l + (int) (Math.random() * (r - l + 1)));
        partition(array, l, r, x, comparator);
        int left = first;
        int right = last;
        quickSort(array, l, left - 1, comparator);
        quickSort(array, right + 1, r, comparator);
    }

    public static void partition(Object array, int l, int r, Object x, Comparator c) {
        first = l;
        last = r;
        int i = l;
        while (i <= last) {
            if (c.compare(Array.get(array, i), x) == 0) {
                i++;
            } else if (c.compare(Array.get(array, i), x) < 0) {
                swap(array, first++, i++);
            } else {
                swap(array, i, last--);
            }
        }
    }

    public static void swap(Object arr, int i, int j) {
        Object tmp = Array.get(arr, i);
        Array.set(arr, i, Array.get(arr, j));
        Array.set(arr, j, tmp);
    }

    // for test
    public static void testLA() throws ClassNotFoundException {
        String s = "[J";
        Object array = Array.newInstance(Class.forName(s).getComponentType(), 10);
        for (int i = 0; i < 10; i++) {
            Array.setLong(array, i, (long) (Math.random() * Long.MAX_VALUE));
        }

        System.out.printf(Arrays.toString((long[]) array));
        sort(array, Comparator.naturalOrder());
        System.out.println();
        System.out.printf(Arrays.toString((long[]) array));
    }
    // for test
    public static void testIA() throws ClassNotFoundException {
        String s = "[I";
        Object array = Array.newInstance(Class.forName(s).getComponentType(), 10);
        for (int i = 0; i < 10; i++) {
            Array.set(array, i, (int) (Math.random() * 10));
        }

        System.out.printf(Arrays.toString((int[]) array));
        sort(array, Comparator.naturalOrder());
        System.out.println();
        System.out.printf(Arrays.toString((int[]) array));
    }
    // for test
    public static void testBA() throws ClassNotFoundException {
        String s = "[B";
        Object array = Array.newInstance(Class.forName(s).getComponentType(), 10);
        for (int i = 0; i < 10; i++) {
            Array.set(array, i, (byte) (Math.random() * 10));
        }

        System.out.printf(Arrays.toString((byte[]) array));
        sort(array, Comparator.naturalOrder());
        System.out.println();
        System.out.printf(Arrays.toString((byte[]) array));
    }
    // for test
    public static void testCA() throws ClassNotFoundException {
        String s = "[C";
        Character[] c = new Character[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o'};
        Object array = Array.newInstance(Class.forName(s).getComponentType(), 10);
        for (int i = 0; i < 10; i++) {
            Array.set(array, i, c[(int) (Math.random() * 10)]);
        }

        System.out.printf(Arrays.toString((char[]) array));
        sort(array, Comparator.naturalOrder());
        System.out.println();
        System.out.printf(Arrays.toString((char[]) array));
    }

    // for test
    public static void main(String[] args) throws ClassNotFoundException {
        testCA();
    }
}
