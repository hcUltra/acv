package org.ultra.validator. core.parse;

import org.ultra.validator. config.ArgumentConfig;
import org.ultra.validator.config.Pairs;
import org.ultra.validator. enums.ArgumentTypeEnum;
import org.ultra.validator. exception.UnableResolveTypeException;
import org.ultra.validator. exception.UnknownTypeException;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import static org.ultra.validator.core.parse.Parser.flatteningMap;

/**
 * @author yinger
 * @description TODO
 * @date 2024/12/30
 **/
public final class GenericCollectionParser {
    public static void _fromCollection(ArgumentConfig config) throws UnableResolveTypeException, UnknownTypeException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, UnknownTypeException {
        config.setIThCollection(Parser.times[config.getDepth()]++);
        config.setArgumentType(ArgumentTypeEnum.COLLECTION);
        // 获取集合的泛型信息
        Type type = config.getType();
        config.setClassName(((ParameterizedTypeImpl) type).getRawType().getName());
        // 递进前先设置Type
        if (config.getInnerConfig() == null || config.getInnerConfig().length == 0) {
            config.setInnerConfig(new ArgumentConfig[]{new ArgumentConfig()});
        }
        config.getInnerConfig()[0].setType(((ParameterizedTypeImpl) type).getActualTypeArguments()[0]);
        config.getInnerConfig()[0].setDepth(config.getDepth() + 1);
        config.getInnerConfig()[0].setIThArgument(config.getIThArgument());
        flatteningMap.put(new Pairs(config.getIThArgument(),config.getDepth(),config.getIThCollection()),config);
        Parser.parserDispatcher(config.getInnerConfig()[0]);
    }
}




