//package org.zhc.acvserver. parse;
//
//import org.zhc.acvserver. data.Arguments;
//import org.zhc.acvserver. range.Range;
//import org.zhc.acvserver. exception.UnableResolveTypeException;
//import org.zhc.acvserver. exception.UnknownTypeException;
//
//import java.lang.reflect.Array;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Type;
//
///**
// * @author yinger
// * @description TODO
// * @date 2024/12/30
// **/
//public final class ArrayParser {
//    /**
//     * 从数组中创建对象数组，并填充对象数组
//     *
//     * @param groupsNumber 组数
//     * @param type         类型
//     * @param index        参数索引
//     * @param used         使用次数
//     * @param arguments    参数列表
//     * @return 对象数组
//     * @throws UnableResolveTypeException 无法解析类型异常
//     * @throws UnknownTypeException       未知类型异常
//     * @throws ClassNotFoundException     类未找到异常
//     * @throws InvocationTargetException  调用目标异常
//     * @throws NoSuchMethodException      无此方法异常
//     * @throws InstantiationException     初始化异常
//     * @throws IllegalAccessException     访问权限异常
//     * @throws UnknownTypeException       未知类型异常
//     */
//    public static Object[] _fromArray(int groupsNumber, Type type, int index, int used, Arguments... arguments) throws UnableResolveTypeException, UnknownTypeException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, UnknownTypeException {
//        // 创建对象数组
//        Object[] result = new Object[groupsNumber];
//
//        // 获取数据量
//        Range volume = arguments[index].getDataConstraint().getInitialSize()[used];
//        // 计算得到size后,检查当前size 是否不符合输入要求
//        int size = -1;
//
//        size = (int) Range.getNumberMinToMax(volume);
//
//        // 创建对象数组
//        for (int i = 0; i < result.length; i++) {
//            result[i] = Array.newInstance(((Class<?>) type).getComponentType(), size);
//        }
//
//        // 填充对象数组
//        for (int i = 0; i < size; i++) {
//            // 解析对象数组
//            Object[] element = Parser.parser(groupsNumber, ((Class<?>) type).getComponentType(), index, used + 1, arguments);
//            Parser.id.incrementAndGet();
//            // 填充对象数组
//            for (int j = 0; j < result.length; j++) {
//                Array.set(result[j], i, element[j]);
//            }
//        }
//        // TODO isSorted
//        // 如果要求有序,对数组进行排序操作
//        return result;
//    }
//}
