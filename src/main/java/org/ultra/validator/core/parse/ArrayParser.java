package org.ultra.validator.core.parse;

import org.ultra.validator.config.ArgumentConfig;
import org.ultra.validator.config.Pairs;
import org.ultra.validator.enums.ArgumentTypeEnum;
import org.ultra.validator.exception.UnableResolveTypeException;
import org.ultra.validator.exception.UnknownTypeException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import static org.ultra.validator.core.parse.Parser.flatteningMap;

/**
 * @author yinger
 * @description TODO
 * @date 2024/12/30
 **/
public final class ArrayParser {
    /**
     * 从数组中创建对象数组，并填充对象数组
     *
     * @throws UnableResolveTypeException 无法解析类型异常
     * @throws UnknownTypeException       未知类型异常
     * @throws ClassNotFoundException     类未找到异常
     * @throws InvocationTargetException  调用目标异常
     * @throws NoSuchMethodException      无此方法异常
     * @throws InstantiationException     初始化异常
     * @throws IllegalAccessException     访问权限异常
     * @throws UnknownTypeException       未知类型异常
     */
    public static void _fromArray(ArgumentConfig config) throws UnableResolveTypeException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, UnknownTypeException {
        config.setIThCollection(Parser.times[config.getDepth()]++);
        config.setArgumentType(ArgumentTypeEnum.ARRAY);
        Type type = config.getType();
        config.setClassName(((Class<?>) type).getName());
        if (config.getInnerConfig() == null || config.getInnerConfig().length == 0) {
            config.setInnerConfig(new ArgumentConfig[]{new ArgumentConfig()});
        }
        config.getInnerConfig()[0].setType(((Class<?>) type).getComponentType());
        config.getInnerConfig()[0].setDepth(config.getDepth() + 1);
        config.getInnerConfig()[0].setIThArgument(config.getIThArgument());


        flatteningMap.put(new Pairs(config.getIThArgument(),config.getDepth(),config.getIThCollection()),config);
        Parser.parserDispatcher(config.getInnerConfig()[0]);
    }
}