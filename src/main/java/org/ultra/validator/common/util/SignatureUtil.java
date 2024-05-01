package org.ultra.validator.common.util;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;

/**
 * @author yinger
 * @description 封装签名操作
 * @date 2024/1/1 12:04
 **/
public class SignatureUtil {
    /**
     * 根据给定的Type类型获取对应的泛型数组签名字符串
     *
     * @param type 泛型类型
     * @return 对应的泛型数组签名字符串
     */
    public static String getGenericArraySignatureByType(Type type) {
        assert type instanceof GenericArrayType;

        StringBuilder s = new StringBuilder();
        String fullClassName = type.getTypeName();
        String dimensions = type.getTypeName();
        // 获取泛型参数开始的位置
        for (int i = 0; i < fullClassName.length(); i++) {
            if ('<' != fullClassName.charAt(i)) {
                s.append(fullClassName.charAt(i));
            } else {
                break;
            }
        }
        int count = 0;
        // 去掉维度为数组的中括号，保留类名之前的空数组中括号
        for (int i = dimensions.length() - 1; i >= 0; i -= 2) {
            if (']' == dimensions.charAt(i) && '[' == dimensions.charAt(i - 1)) {
                count++;
            } else {
                break;
            }
        }
        StringBuilder ret = new StringBuilder();
        // 添加维度为数组的中括号
        for (int i = 0; i < count; i++) {
            ret.append("[");
        }
        ret.append("L");
        // full class name
        ret.append(s);
        ret.append(";");
        return ret.toString();
    }
}
