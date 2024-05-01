package org.ultra.validator. data;

import lombok.Data;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ultra.validator.common.util.UnsafeUtil;
import org.ultra.validator. core.parse.BasicParser;

import java.lang.reflect.Type;
import java.util.*;

/**
 * 进行复杂对象的比较(含 code字段)
 * 主要用于比较结果集
 * 引用类型 默认比较code & value
 * 基本类型 值比较 value
 */
@Data
@SuppressWarnings("all")
public class DeepEquals {
    public static int count = 0;
    private static final ObjectMapper o = new ObjectMapper();

    @SuppressWarnings("unchecked")
    public static boolean deepEquals(Object o1, Object o2) throws JsonProcessingException {
        // 如果两个对象引用相同，直接返回true
        if (o1 == o2) {
            return true;
        }
        // 如果两个对象为null，返回false
        if (o1 == null || o2 == null) {
            return false;
        }
        // 如果两个对象的类不同，返回false
        if (!o1.getClass().equals(o2.getClass())) {
            return false;
        }

        // 如果一个是字符串，另一个不是，返回false
        if (o1 instanceof String && !(o2 instanceof String)) {
            return false;
        }
        // 如果一个是数组，另一个不是，返回false
        if (o1.getClass().isArray() && !(o2.getClass().isArray())) {
            return false;
        }
        // 如果一个是集合，另一个不是，返回false
        if (o1 instanceof Collection && !(o2 instanceof Collection)) {
            return false;
        }
        // 如果一个是Map，另一个不是，返回false
        if (o1 instanceof Map && !(o2 instanceof Map)) {
            return false;
        }

        if (o1 instanceof Map) {
            return deepEqualsHelper((Map<?, ?>) o1, (Map<?, ?>) o2) &&
                    deepEquals(((Map<?, ?>) o1).keySet(), ((Map<?, ?>) o2).keySet()) &&
                    deepEquals(((Map<?, ?>) o1).values(), ((Map<?, ?>) o2).values());
        } else if (o1 instanceof Collection) {
            return deepEquals(((Collection<?>) o1), ((Collection<?>) o2));
        } else if (o1.getClass().isArray()) {
            try {
                assert o1 instanceof Object[];
                return deepEquals((Object[]) o1, (Object[]) o2);
            } catch (ClassCastException e) {
                return deepEqualsArray(o1, o2);
            }
        }

        return deepEqualsBasic(o1, o2);
    }

    private static boolean deepEqualsBasic(Object o1, Object o2) throws JsonProcessingException {
        Type type1 = o1.getClass();
        Type type2 = o2.getClass();
        if (BasicParser.isWrapperClass(type1) && BasicParser.isWrapperClass(type2)) {
            if (!o1.equals(o2) || UnsafeUtil.hashcode(o1) != UnsafeUtil.hashcode(o2)) {
                System.out.println("o1 value" + o1 + "o1 code:" + UnsafeUtil.hashcode(o1));
                System.out.println("o1 value" + o2 + "o2 code:" + UnsafeUtil.hashcode(o2));
                return false;
            }
        }

        return true;
    }

    private static boolean deepEqualsHelper(Map<?, ?> o1, Map<?, ?> o2) throws JsonProcessingException {
        if (o1.size() != o2.size()) {
            return false;
        }

        for (Object key : o1.keySet()) {
            if (!deepEquals(o1.get(key), o2.get(key))) {
                return false;
            }
        }
        return true;
    }

    private static boolean deepEquals(Collection<?> o1, Collection<?> o2) throws JsonProcessingException {
        if (o1.size() != o2.size()) {
            return false;
        }


        Iterator<?> it1 = o1.iterator();
        Iterator<?> it2 = o2.iterator();

        while (it1.hasNext() && it2.hasNext()) {
            if (!deepEqualsBasic(it1.next(), it2.next())) {
                return false;
            }
        }

        it1 = o1.iterator();
        it2 = o2.iterator();
        while (it1.hasNext() && it2.hasNext()) {
            if (!deepEquals(it1.next(), it2.next())) {
                return false;
            }
        }

        for (Object obj : o1) {
            if (!o2.contains(obj)) {
                return false;
            }
        }

        return true;
    }


    private static boolean deepEquals(Object[] o1, Object[] o2) throws JsonProcessingException {
        if (o1.length != o2.length) {
            return false;
        }

        for (int i = 0; i < o1.length; i++) {
            if (!deepEqualsBasic(o1[i], o2[i])) {
                System.out.println(UnsafeUtil.hashcode(o1));
                System.out.println(UnsafeUtil.hashcode(o2));
                return false;
            }
            if (!deepEquals(o1[i], o2[i])) {
                return false;
            }
        }
        return true;
    }

    private static boolean deepEqualsArray(Object o1, Object o2) {
        if (o1 instanceof int[]) {
            int[] array1 = (int[]) o1;
            int[] array2 = (int[]) o2;
            if (array1.length != array2.length) {
                return false;
            }

            return Arrays.equals(array1, array2);
        } else if (o1 instanceof byte[]) {
            byte[] array1 = (byte[]) o1;
            byte[] array2 = (byte[]) o2;
            if (array1.length != array2.length) {
                return false;
            }

            return Arrays.equals(array1, array2);
        } else if (o1 instanceof short[]) {
            short[] array1 = (short[]) o1;
            short[] array2 = (short[]) o2;
            if (array1.length != array2.length) {
                return false;
            }
            return Arrays.equals(array1, array2);
        } else if (o1 instanceof char[]) {
            char[] array1 = (char[]) o1;
            char[] array2 = (char[]) o2;
            if (array1.length != array2.length) {
                return false;
            }
            return Arrays.equals(array1, array2);
        } else if (o1 instanceof long[]) {
            long[] array1 = (long[]) o1;
            long[] array2 = (long[]) o2;
            if (array1.length != array2.length) {
                return false;
            }

            return Arrays.equals(array1, array2);
        } else if (o1 instanceof float[]) {
            float[] array1 = (float[]) o1;
            float[] array2 = (float[]) o2;
            if (array1.length != array2.length) {
                return false;
            }

            return Arrays.equals(array1, array2);
        } else if (o1 instanceof double[]) {
            double[] array1 = (double[]) o1;
            double[] array2 = (double[]) o2;
            if (array1.length != array2.length) {
                return false;
            }
            return Arrays.equals(array1, array2);
        } else if (o1 instanceof boolean[]) {
            boolean[] array1 = (boolean[]) o1;
            boolean[] array2 = (boolean[]) o2;
            if (array1.length != array2.length) {
                return false;
            }
            return Arrays.equals(array1, array2);
        }

        throw new RuntimeException("deep array compare failed");
    }
}
