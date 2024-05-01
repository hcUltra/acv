//package org.zhc.acvserver. parse;
//
//import org.zhc.acvserver. common.UnsafeUtil;
//import org.zhc.acvserver. data.Arguments;
//import org.zhc.acvserver. range.Range;
//import org.zhc.acvserver. exception.UnableResolveTypeException;
//import org.zhc.acvserver. exception.UnknownTypeException;
//
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Type;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * @author yinger
// * @description TODO
// * @date 2024/12/30
// **/
//public final class BasicParser {
//    public static boolean isWrapperClass(Type type) {
//        if (type instanceof Class) {
//            Class<?> clazz = (Class<?>) type;
//            if (clazz.isPrimitive()) {
//                return false;
//            }
//            String name = clazz.getName();
//            List<String> list = Arrays.asList("java.lang.Boolean", "java.lang.Byte", "java.lang.Short", "java.lang.Integer", "java.lang.Long", "java.lang.Float", "java.lang.Double", "java.lang.Character", "java.lang.String");
//            // 判断是否是基本类型的包装类
//            for (String s : list) {
//                if (name.equals(s)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//
//    public static Object[] createLeafObject(int groupsNumber, Type type, int index, int used, Arguments... arguments) {
//        Object[] result = new Object[groupsNumber];
//        Range scope = arguments[index].getDataConstraint().getInitialScope()[used];
//        if (type instanceof Class<?> && ((Class<?>) type).isPrimitive()) {
//            // 1. 基本类型
//            switch (type.toString()) {
//                case "int":
//                    int intValue = (int) Range.getNumberMinToMax(scope);
//                    Arrays.fill(result, intValue);
//                    break;
//                case "boolean":
//                    boolean booleanValue = Math.random() < 0.5;
//                    Arrays.fill(result, booleanValue);
//                    break;
//                case "byte":
//                    byte byteValue = (byte) Range.getNumberMinToMax(scope);
//                    Arrays.fill(result, byteValue);
//                    break;
//                case "char":
//                    char charValue = (char) Range.getNumberMinToMax(scope);
//                    Arrays.fill(result, charValue);
//                    break;
//                case "short":
//                    short shortValue = (short) Range.getNumberMinToMax(scope);
//                    Arrays.fill(result, shortValue);
//                    break;
//                case "long":
//                    long longValue = (long) Range.getNumberMinToMax(scope);
//                    Arrays.fill(result, longValue);
//                    break;
//                case "float":
//                    float floatValue = (float) Range.getNumberMinToMax(scope);
//                    Arrays.fill(result, floatValue);
//                    break;
//                case "double":
//                    double doubleValue = Range.getNumberMinToMax(scope);
//                    Arrays.fill(result, doubleValue);
//                default:
//                    throw new RuntimeException("Unknown Error!");
//            }
//        } else if (BasicParser.isWrapperClass(type)) {
//            // 2. 包装类
//            switch (type.toString().substring(6)) {
//                case "java.lang.Integer":
//                    int intValue = (int) (Range.getNumberMinToMax(scope));
//                    for (int i = 0; i < result.length; i++) {
//                        result[i] = new Integer(intValue);
//                    }
//                    break;
//                case "java.lang.Boolean":
//                    boolean booleanValue = Math.random() < 0.5;
//                    for (int i = 0; i < result.length; i++) {
//                        result[i] = new Boolean(booleanValue);
//                        UnsafeUtil.setHashcode(result[i], Parser.id.get());
//                    }
//                    break;
//                case "java.lang.Byte":
//                    byte byteValue = (byte) (Range.getNumberMinToMax(scope));
//                    for (int i = 0; i < result.length; i++) {
//                        result[i] = new Byte(byteValue);
//                        UnsafeUtil.setHashcode(result[i], Parser.id.get());
//                    }
//                    break;
//                case "java.lang.Character":
//                    char charValue = (char) (Range.getNumberMinToMax(scope));
//                    for (int i = 0; i < result.length; i++) {
//                        result[i] = new Character(charValue);
//                        UnsafeUtil.setHashcode(result[i], Parser.id.get());
//                    }
//                case "java.lang.Short":
//                    short shortValue = (short) (Range.getNumberMinToMax(scope));
//                    for (int i = 0; i < result.length; i++) {
//                        result[i] = new Short(shortValue);
//                        UnsafeUtil.setHashcode(result[i], Parser.id.get());
//                    }
//                    break;
//                case "java.lang.Long":
//                    long longValue = (long) (Range.getNumberMinToMax(scope));
//                    for (int i = 0; i < result.length; i++) {
//                        result[i] = new Long(longValue);
//                        UnsafeUtil.setHashcode(result[i], Parser.id.get());
//                    }
//                    break;
//                case "java.lang.Float":
//                    float floatValue = (float) (Range.getNumberMinToMax(scope));
//                    for (int i = 0; i < result.length; i++) {
//                        result[i] = new Float(floatValue);
//                        UnsafeUtil.setHashcode(result[i], Parser.id.get());
//                    }
//                    break;
//                case "java.lang.Double":
//                    double doubleValue = Range.getNumberMinToMax(scope);
//                    for (int i = 0; i < result.length; i++) {
//                        result[i] = new Double(doubleValue);
//                        UnsafeUtil.setHashcode(result[i], Parser.id.get());
//                    }
//                    break;
//                default:
//                    System.out.println(type);
//                    throw new RuntimeException("Unknown Error!");
//            }
//        } else {
//            // 3. 自定义类
//            System.out.println("Customize");
//            // 反射获取value字段和id字段
//        }
//        return result;
//    }
//
//    // 拿到字符数组 字符数组不能让你走到这儿
//    public static Object[] _fromString(int groupsNumber, Type type, int index, int used, Arguments... arguments) throws UnableResolveTypeException, UnknownTypeException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, UnknownTypeException {
//        return null;
//    }
//}
