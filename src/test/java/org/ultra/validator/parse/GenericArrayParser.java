//package org.zhc.acvserver. parse;
//
//import org.zhc.acvserver. common.SignatureUtil;
//import org.zhc.acvserver. data.Arguments;
//import org.zhc.acvserver. range.Range;
//import org.zhc.acvserver. exception.UnableResolveTypeException;
//import org.zhc.acvserver. exception.UnknownTypeException;
//
//import java.lang.reflect.Array;
//import java.lang.reflect.GenericArrayType;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Type;
//
///**
// * @author yinger
// * @description TODO
// * @date 2024/12/30
// **/
//public final class GenericArrayParser {
//    public static Object[] _fromGenericArray(int groupsNumber, Type type, int index, int used, Arguments... arguments) throws UnableResolveTypeException, UnknownTypeException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, UnknownTypeException {
//        Object[] result = new Object[groupsNumber];
//
//        // 根据type和签名,获取全类名
//        String fullClassNameStr = SignatureUtil.getGenericArraySignatureByType(type);
//        //获取数组组件类型
//        Class<?> componentType = Class.forName(fullClassNameStr).getComponentType();
//
//        // 数据量
//        Range volume = arguments[index].getDataConstraint().getInitialSize()[used];
//        int size = (int) Range.getNumberMinToMax(volume);
//
//        // create array
//        for (int i = 0; i < result.length; i++) {
//            result[i] = Array.newInstance(componentType, size);
//        }
//
//        // fill array
//        for (int i = 0; i < size; i++) {
//            Object[] tmp = Parser.parser(groupsNumber, ((GenericArrayType) type).getGenericComponentType(), index, used + 1, arguments);
//            Parser.id.incrementAndGet();
//            for (int j = 0; j < result.length; j++) {
//                Array.set(result[j], i, tmp[j]);
//            }
//        }
//
//        return result;
//    }
//}
