package org.ultra.validator. core.parse;

import org.ultra.validator. config.ArgumentConfig;
import org.ultra.validator.config.Pairs;
import org.ultra.validator. enums.ArgumentTypeEnum;
import org.ultra.validator. exception.UnableResolveTypeException;
import org.ultra.validator. exception.UnknownTypeException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import static org.ultra.validator.core.parse.Parser.flatteningMap;

/**
 * @author yinger
 * @description TODO
 * @date 2024/12/30
 **/
public final class BasicParser {
    public static boolean isWrapperClass(Type type) {
        if (type instanceof Class) {
            Class<?> clazz = (Class<?>) type;
            if (clazz.isPrimitive()) {
                return false;
            }
            String name = clazz.getName();
            List<String> list = Arrays.asList("java.lang.Boolean", "java.lang.Byte", "java.lang.Short", "java.lang.Integer", "java.lang.Long", "java.lang.Float", "java.lang.Double", "java.lang.Character", "java.lang.String");
            // 判断是否是基本类型的包装类
            for (String s : list) {
                if (name.equals(s)) {
                    return true;
                }
            }
        }
        return false;
    }


    public static void createLeafObject(ArgumentConfig config) {
        config.setIThCollection(Parser.times[config.getDepth()]++);
        flatteningMap.put(new Pairs(config.getIThArgument(),config.getDepth(),config.getIThCollection()),config);
        Type type = config.getType();
        if (type instanceof Class<?> && ((Class<?>) type).isPrimitive()) {
            config.setClassName(type.toString());
            config.setArgumentType(ArgumentTypeEnum.PRIMITIVE);
        } else if (BasicParser.isWrapperClass(type)) {
            config.setArgumentType(ArgumentTypeEnum.WRAPPER);
            config.setClassName(type.toString().substring(6));
        } else {
            // 3. 自定义类
            // TODO 待处理 例如 String
            System.out.println("Customize 自定义类 ... ");
            // 反射获取value字段和id字段
        }
    }

    public static void _fromCollection(ArgumentConfig config) throws UnableResolveTypeException, UnknownTypeException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        config.setArgumentType(ArgumentTypeEnum.COLLECTION);
        config.setClassName(String.class.getName());
        if (config.getInnerConfig() == null || config.getInnerConfig().length == 0) {
            config.setInnerConfig(new ArgumentConfig[]{new ArgumentConfig()});
        }
        config.getInnerConfig()[0].setType(char.class);
        Parser.parserDispatcher(config.getInnerConfig()[0]);
    }
}
