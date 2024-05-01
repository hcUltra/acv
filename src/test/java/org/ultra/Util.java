package org.ultra;

import lombok.Data;
import org.ultra.validator.common.util.ReflectUtil;
import org.ultra.validator.common.util.SignatureUtil;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author yinger
 * @description TODO
 * @date 2024/1/10 15:31
 **/
@Data
@SuppressWarnings("all")
public class Util {
//        private static void test(List<Map<Integer[], Integer>>[] collection) {
//        // 通过反射获取构造随机样本的模板
//    }

    private static void test(ArrayList<ArrayList<Integer>>[][] collection) {
        // 通过反射获取构造随机样本的模板
    }


    public static void main(String[] args) throws ClassNotFoundException {
        Method method = ReflectUtil.getMethod(Util.class, "test");
        Type[] types = method.getGenericParameterTypes();
        List<JavaType> res = new ArrayList<>();
        int i = 0;

        for (Type type : types) {
            res.add(parser(type));
        }


        Class<?> clazz = Class.forName(((ArrayType) (res.get(0))).getSignature());
        Object o = Array.newInstance(clazz, 12);

        System.out.println(res);
    }

    /**
     * 解析类型
     *
     * @param type 类型
     * @return 解析后的JavaType
     * @throws ClassNotFoundException 如果类型未找到，则抛出ClassNotFoundException异常
     */

    public static JavaType parser(Type type) throws ClassNotFoundException {
        if (type == null) {
            throw new IllegalArgumentException("type is null");
        }
        if (type instanceof Class<?> && ((Class<?>) type).isArray()) {
            // 判断是否为数组类型
            ArrayType arrayType = new ArrayType(((Class<?>) type).getName());
            // 创建数组类型
            // 组件类型
            JavaType componentType = parser(((Class<?>) type).getComponentType());
            // 解析组件类型
            arrayType.setComponentType(componentType);
            // 设置组件类型
            return arrayType;
        } else if (type instanceof GenericArrayType) {
            // 判断是否为泛型数组类型
            ArrayType arrayType = new ArrayType(SignatureUtil.getGenericArraySignatureByType(type));
            // 创建泛型数组类型
            // 解析组件类型
            JavaType componentType = parser(Class.forName(arrayType.getSignature()).getComponentType());
            // 解析组件类型
            arrayType.setComponentType(componentType);
            // 设置组件类型
            return arrayType;
        } else if (type instanceof ParameterizedType) {
            // 判断是否为参数化类型
            Class<?> clazz = Class.forName(((ParameterizedType) type).getRawType().getTypeName());
            // 获取原始类型
            if (Collection.class.isAssignableFrom(clazz)) {
                // 判断是否为集合类型
                CollectionType collectionType = new CollectionType(((ParameterizedType) type).getRawType().getTypeName());
                // 创建集合类型
                // 解析实际类型
                JavaType actualType = parser(((ParameterizedType) type).getActualTypeArguments()[0]);
                // 解析实际类型
                collectionType.setActuallyType(actualType);
                // 设置实际类型
                return collectionType;
            } else if (Map.class.isAssignableFrom(clazz)) {
                // 判断是否为映射类型
                MapType mapType = new MapType(((ParameterizedType) type).getRawType().getTypeName());
                // 创建映射类型
                // 解析键类型
                JavaType keyType = parser(((ParameterizedType) type).getActualTypeArguments()[0]);
                // 解析键类型
                // 解析值类型
                JavaType valueType = parser(((ParameterizedType) type).getActualTypeArguments()[1]);
                // 解析值类型
                mapType.setKeyActuallyType(keyType);
                // 设置键类型
                mapType.setValueActuallyType(valueType);
                // 设置值类型
                return mapType;
            }
        } else if (type instanceof Class) {
            // 判断是否为普通类类型(视作叶子节点进行创建根，递归出口)
            ClassType classType = new ClassType(type.getTypeName());
            // 创建普通类类型
            return classType;
        } else {
            throw new IllegalArgumentException("type is not support");
        }
        return null;
    }
}