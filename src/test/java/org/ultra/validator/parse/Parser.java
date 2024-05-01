//package org.zhc.acvserver. parse;
//
//import org.zhc.acvserver. common.ReflectUtil;
//import org.zhc.acvserver. config.ValidatorConfig;
//import org.zhc.acvserver. data.Arguments;
//import org.zhc.acvserver. exception.UnknownTypeException;
//import org.zhc.acvserver. exception.UnableResolveTypeException;
//
//import java.lang.reflect.*;
//import java.util.*;
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * @author yinger
// * @description TODO
// * @date 2024/12/30
// **/
//public final class Parser {
//    // 定义原子类自增成员变量
//    public static volatile AtomicInteger id = new AtomicInteger(0);
//
//    /**
//     * 一次性生产 n 组测试样本 2 ≤ n ≤ 10 (安全机制) -> 扩展项
//     *
//     * @param groupsNumber 组数
//     * @param type         类型
//     * @param index        索引
//     * @param used         使用状态
//     * @param arguments    参数
//     * @return 结果对象数组
//     * @throws UnknownTypeException       未知类型异常
//     * @throws UnableResolveTypeException 无法解析类型异常
//     * @throws ClassNotFoundException     类未找到异常
//     * @throws InvocationTargetException  调用目标异常
//     * @throws NoSuchMethodException      无此类方法异常
//     * @throws InstantiationException     实例化异常
//     * @throws IllegalAccessException     访问权限异常
//     */
//    public static Object[] parser(int groupsNumber, Type type, int index, int used, Arguments... arguments)
//            throws UnknownTypeException,
//            UnableResolveTypeException,
//            ClassNotFoundException,
//            InvocationTargetException,
//            NoSuchMethodException,
//            InstantiationException,
//            IllegalAccessException {
//        if (groupsNumber < 2 || groupsNumber > 10) {
//            throw new IllegalArgumentException("样本大小需要再 [2,10] 之间! " + groupsNumber);
//        }
//        Object[] result = null;
//        if (type instanceof Class<?>) {
//            if (((Class<?>) type).getComponentType() != null) {
////            if (((Class<?>) type).isArray()) {
//                // int[]
//                result = ArrayParser._fromArray(groupsNumber, type, index, used, arguments);
//            } else {
//                // int or Integer  -> leaf
//                if (((Class<?>) type).getName().equals("java.lang.String")) {
//                    System.out.println("String");
//                    result = BasicParser._fromString(groupsNumber, type, index, used, arguments);
//                } else {
//                    result = BasicParser.createLeafObject(groupsNumber, type, index, used, arguments);
//                }
//            }
//        } else if (type instanceof ParameterizedType) {
//            // List<Integer>
//            Class<?> clazz = Class.forName(((ParameterizedType) type).getRawType().getTypeName());
//            if (Collection.class.isAssignableFrom(clazz)) {
//                // Collection 接口
//                result = CollectionParser._fromCollection(groupsNumber, type, index, used, arguments);
//            } else if (Map.class.isAssignableFrom(clazz)) {
//                // Map 接口
//                result = MapParser._fromMap(groupsNumber, type, index, used, arguments);
//            } else {
//                // 自定义泛型集合
//                // 反射 id & value 字段
//
//            }
//        } else if (type instanceof java.lang.reflect.GenericArrayType) {
//            // List<Integer>[]
//            result = GenericArrayParser._fromGenericArray(groupsNumber, type, index, used, arguments);
//        } else if (type instanceof TypeVariable<?>) {
//            // List<T>
//            throw new UnableResolveTypeException("TypeVariable");
//        } else if (type instanceof WildcardType) {
//            // List<?>
//            throw new UnableResolveTypeException("WildcardType");
//        } else {
//            // ...
//            throw new UnknownTypeException("UnknownType " + type);
//        }
//        return result;
//    }
//
//    /**
//     * 预处理参数
//     *
//     * @param groupsNumber 组数
//     * @param config       配置
//     * @param arguments    参数
//     * @return 处理好的参数
//     * @throws UnableResolveTypeException 无法解析类型异常
//     * @throws UnknownTypeException       未知类型异常
//     * @throws ClassNotFoundException     类未找到异常
//     * @throws InvocationTargetException  调用目标异常
//     * @throws NoSuchMethodException      无此类方法异常
//     */
//    public static Object[][] preParser(int groupsNumber, ValidatorConfig config, Arguments... arguments) throws
//            UnableResolveTypeException,
//            UnknownTypeException,
//            ClassNotFoundException,
//            InvocationTargetException,
//            NoSuchMethodException,
//            InstantiationException,
//            IllegalAccessException {
//        // 每个参数对应一个 type
//        // int add (int a, int b) => type[0] = int, type[1] = int
//        Type[] types = ReflectUtil.getMethod(config.get_clazz(), config.get_validatorMethodName()[0]).getGenericParameterTypes();
//
//        Object[][] args = new Object[types.length][];
//        for (int i = 0; i < types.length; i++) {
//            args[i] = parser(groupsNumber, types[i], i, 0, arguments);
//        }
//        return processArguments(args);
//    }
//
//    /**
//     * 矩阵转置
//     *
//     * @param args 原始矩阵
//     * @return 转置后的矩阵
//     */
//    private static Object[][] processArguments(Object[][] args) {
//        // 矩阵转置
//        Object[][] result = new Object[args[0].length][args.length];
//        for (int i = 0; i < args.length; i++) {
//            for (int j = 0; j < args[0].length; j++) {
//                result[j][i] = args[i][j];
//            }
//        }
//        return result;
//    }
//}