//package org.zhc.acvserver. parse;
//
//import org.zhc.acvserver. common.ReflectUtil;
//import org.zhc.acvserver. data.Arguments;
//import org.zhc.acvserver. range.Range;
//import org.zhc.acvserver. exception.UnableResolveTypeException;
//import org.zhc.acvserver. exception.UnknownTypeException;
//
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//import java.util.Map;
//
///**
// * @author yinger
// * @description TODO
// * @date 2024/12/30
// **/
//public final class MapParser {
//    @SuppressWarnings("unchecked")
//    public static Object[] _fromMap(int groupsNumber, Type type, int index, int used, Arguments... arguments) throws UnableResolveTypeException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, UnknownTypeException {
//        Object[] result = new Object[groupsNumber];
//        // 数据量 - 键值对数量
//        Range volume = arguments[index].getDataConstraint().getInitialSize()[used];
//        int size = (int) Range.getNumberMinToMax(volume);
//
//        // create map
//        for (int i = 0; i < result.length; i++) {
//            result[i] = ReflectUtil.getNonParameterInstance(((ParameterizedType) type).getRawType().getTypeName());
//        }
//        // 获取泛型
//        Type[] types = ((ParameterizedType) type).getActualTypeArguments();
//        // 获取key 和 value 的类型
//        Type keyType = types[0];
//        Type valueType = types[1];
//
//        for (int i = 0; i < size; i++) {
//            Object[] rigthKey;
//
//            long start = System.currentTimeMillis();
//            do {
//                // 获取 一组 key
//                rigthKey = Parser.parser(groupsNumber, keyType, index, used + 1, arguments);
//                Parser.id.incrementAndGet();
//                long end = System.currentTimeMillis();
//                if (end - start > 10_000) { // 10s 超时
//                    throw new IllegalArgumentException("无法得到足够多的key");
//                }
//            } while (((Map<Object, Object>) result[0]).containsKey(rigthKey[0]));
//
//            // 获取一组 value
//            Object[] rightValue = Parser.parser(groupsNumber, valueType, index, used + 2, arguments);
//            Parser.id.incrementAndGet();
//            for (int j = 0; j < result.length; j++) {
//                ((Map<Object, Object>) result[j]).put(rigthKey[j], rightValue[j]);
//            }
//        }
//
//        return result;
//    }
//}
