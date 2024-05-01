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
//import java.util.Collection;
//
///**
// * @author yinger
// * @description TODO
// * @date 2024/12/30
// **/
//public final class CollectionParser {
//    @SuppressWarnings("unchecked")
//    public static Object[] _fromCollection(int groupsNumber, Type type, int index, int used, Arguments... arguments) throws UnableResolveTypeException, UnknownTypeException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, UnknownTypeException {
//        Object[] result = new Object[groupsNumber];
//        for (int i = 0; i < result.length; i++) {
//            result[i] = ReflectUtil.getNonParameterInstance(((ParameterizedType) type).getRawType().getTypeName());
//        }
//
//        // 数据量
//        Range volume = arguments[index].getDataConstraint().getInitialSize()[used];
//        int size = (int) Range.getNumberMinToMax(volume);
//
//        // type
//        Type[] types = ((ParameterizedType) type).getActualTypeArguments();
//        // size 个
//        for (int i = 0; i < size; i++) {
//            Object[] tmp = Parser.parser(groupsNumber, types[0], index, used + 1, arguments);
//            Parser.id.incrementAndGet();
//            for (int j = 0; j < result.length; j++) {
//                ((Collection<Object>) (result[j])).add(tmp[j]);
//            }
//        }
//        return result;
//    }
//}
