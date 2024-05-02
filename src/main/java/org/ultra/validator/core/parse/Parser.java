package org.ultra.validator.core.parse;

import org.ultra.validator.common.util.ReflectUtil;
import org.ultra.validator.config.ArgumentConfig;
import org.ultra.validator.config.ArgumentsConfig;
import org.ultra.validator.config.Pairs;
import org.ultra.validator.exception.UnknownTypeException;
import org.ultra.validator.exception.UnableResolveTypeException;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yinger
 * @description TODO
 * @date 2024/12/30
 **/
public final class Parser {
    // 下标为深度 值为 k
    public static int[] times = new int[8];// 最大深度 8

    public static Map<Pairs, ArgumentConfig> flatteningMap = new HashMap();

    // 定义原子类自增成员变量
    public static volatile AtomicInteger id = new AtomicInteger(0);

    /**
     * 一次性生产 n 组测试样本 2 ≤ n ≤ 10 (安全机制) -> 扩展项
     *
     * @return 结果对象数组
     * @throws UnknownTypeException       未知类型异常
     * @throws UnableResolveTypeException 无法解析类型异常
     * @throws ClassNotFoundException     类未找到异常
     * @throws InvocationTargetException  调用目标异常
     * @throws NoSuchMethodException      无此类方法异常
     * @throws InstantiationException     实例化异常
     * @throws IllegalAccessException     访问权限异常
     */

    public static void parserDispatcher(ArgumentConfig config/*输出形参数*/)
            throws UnknownTypeException,
            UnableResolveTypeException,
            ClassNotFoundException,
            InvocationTargetException,
            NoSuchMethodException,
            InstantiationException,
            IllegalAccessException {
        Type type = config.getType();
        if ("org.ultra.validator.common.util.ListNode".equals(type.getTypeName())) {
            GenericCollectionParser._fromCollection(config);
        } else {
            if (type instanceof Class<?>) {
                if (((Class<?>) type).getComponentType() != null) {
//            if (((Class<?>) type).isArray()) {
                    // int[]
                    ArrayParser._fromArray(config);
                } else {
                    // int or Integer  -> leaf
                    if (((Class<?>) type).getName().equals("java.lang.String")) {
                        // TODO 待解决 ？
                        BasicParser._fromCollection(config);
                    } else {
                        BasicParser.createLeafObject(config);
                    }
                }
            } else if (type instanceof ParameterizedType) {
                // List<Integer>
                Class<?> clazz = Class.forName(((ParameterizedType) type).getRawType().getTypeName());
                if (Collection.class.isAssignableFrom(clazz)) {
                    // Collection 接口
                    GenericCollectionParser._fromCollection(config);
                } else if (Map.class.isAssignableFrom(clazz)) {
                    // Map 接口
                    MapParser._fromMap(config);
                } else {
                    // 自定义泛型集合
                    // 反射 id & value 字段
                }
            } else if (type instanceof java.lang.reflect.GenericArrayType) {
                // List<Integer>[]
                GenericArrayParser._fromGenericArray(config);
            } else if (type instanceof TypeVariable<?>) {
                // List<T>
                throw new UnableResolveTypeException("TypeVariable");
            } else if (type instanceof WildcardType) {
                // List<?>
                throw new UnableResolveTypeException("WildcardType");
            } else {
                // ...
                throw new UnknownTypeException("UnknownType " + type);
            }
        }

    }

    /**
     * 预处理参数
     *
     * @param configs 配置
     * @return 处理好的参数
     * @throws UnableResolveTypeException 无法解析类型异常
     * @throws UnknownTypeException       未知类型异常
     * @throws ClassNotFoundException     类未找到异常
     * @throws InvocationTargetException  调用目标异常
     * @throws NoSuchMethodException      无此类方法异常
     */
    public static void preParser(ArgumentsConfig configs) throws
            UnableResolveTypeException,
            UnknownTypeException,
            ClassNotFoundException,
            InvocationTargetException,
            NoSuchMethodException,
            InstantiationException,
            IllegalAccessException {
        // 获取被 Solution 类 被 @ValidatorMethod 修饰的所有方法
        Method method = ReflectUtil.reflectValidatorAnnotationMethod();
        // 方法的参数类型
        Type[] types = method.getGenericParameterTypes();
        // TODO 测试
        if (types.length > configs.getArgumentConfigs().size()) {
            configs.setArgumentConfigs(new ArrayList<>(types.length));
        }

        for (int i = 0; i < types.length; i++) {
            configs.getArgumentConfigs().add(new ArgumentConfig());
//            configs.getParameterConfigs().get(i).setType(types[i]);
            // 当前为第 i 个参数
            configs.getArgumentConfigs().get(i).setIThArgument(i);
            // 第一层深度为 0
            configs.getArgumentConfigs().get(i).setDepth(0);
            configs.getArgumentConfigs().get(i).setType(types[i]);
        }
        // 将type根分配到 ArgumentsConfig 的每一项
        for (int i = 0; i < types.length; i++) {
            Arrays.fill(times, 0);
            parserDispatcher(configs.getArgumentConfigs().get(i));
        }
    }
}