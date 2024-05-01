package org.ultra.validator.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * @author yinger
 * @description
 * @date 2024/1/1
 **/
@SuppressWarnings("all")
public class Clone {
    private static volatile Clone clone = null;

    private Clone() {

    }

    public static Clone getInstance() {
        if (clone == null) {
            synchronized (Clone.class) {
                if (clone == null) {
                    clone = new Clone();
                }
            }
        }
        return clone;
    }

    /**
     * 克隆复杂对象(运行时类型)  深拷贝 -> 无法克隆对象头
     *
     * @param type 对象类型
     * @param obj  待克隆对象
     * @return 克隆后的对象
     * @throws IllegalAccessException  当访问修饰符不允许被访问时抛出异常
     * @throws JsonProcessingException 当处理JSON时发生异常时抛出异常
     * @throws NoSuchFieldException    当字段不存在时抛出异常
     */
    public Object clone(Type type, Object obj) throws IllegalAccessException, JsonProcessingException, NoSuchFieldException {
        // 获取去 ObjectMapper 对象
        ObjectMapper objectMapper = new ObjectMapper();

        // 序列化
        String json = objectMapper.writeValueAsString(obj);

        // 获取待克隆对象结构
        TypeReference<Object> typeReference = new TypeReference<Object>() {
        };// 匿名对象

        // 反射修改 _type 字段
        Class<?> clazz = TypeReference.class;

        // 反射修改 typeReference 中的 _type 字段的值
        Field field = clazz.getDeclaredField("_type");

        // 关闭安全访问检查
        field.setAccessible(true);

        field.set(typeReference, type);

        // 反序列化
        return objectMapper.readValue(json, typeReference);
    }
}
