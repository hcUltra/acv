package org.ultra.validator.core.parse;

import org.ultra.validator.config.ArgumentConfig;
import org.ultra.validator.config.Pairs;
import org.ultra.validator.enums.ArgumentTypeEnum;
import org.ultra.validator.exception.UnableResolveTypeException;
import org.ultra.validator.exception.UnknownTypeException;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static org.ultra.validator.core.parse.Parser.flatteningMap;

/**
 * @author yinger
 * @description TODO
 * @date 2024/12/30
 **/
public final class MapParser {
    public static void _fromMap(ArgumentConfig config) throws UnableResolveTypeException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, UnknownTypeException {
        config.setIThCollection(Parser.times[config.getDepth()]++);
        config.setArgumentType(ArgumentTypeEnum.MAP);
        // 获取集合的泛型信息
        Type type = config.getType();
        config.setClassName(((ParameterizedTypeImpl) type).getRawType().getName());
        // 递进前先设置Type
        if (config.getInnerConfig() == null) {
            config.setInnerConfig(new ArgumentConfig[]{null, null});
        }

        if (config.getInnerConfig()[0] == null) {
            config.getInnerConfig()[0] = new ArgumentConfig();
        }

        if (config.getInnerConfig()[1] == null) {
            config.getInnerConfig()[1] = new ArgumentConfig();
        }
        Type[] types = ((ParameterizedType) type).getActualTypeArguments();
        flatteningMap.put(new Pairs(config.getIThArgument(),config.getDepth(),config.getIThCollection()),config);

        config.getInnerConfig()[0].setType(types[0]);
        config.getInnerConfig()[0].setIThArgument(config.getIThArgument());
        config.getInnerConfig()[0].setDepth(config.getDepth() + 1);
        Parser.parserDispatcher(config.getInnerConfig()[0]);

        config.getInnerConfig()[1].setType(types[1]);
        config.getInnerConfig()[1].setIThArgument(config.getIThArgument());
        config.getInnerConfig()[1].setDepth(config.getDepth() + 1);
        Parser.parserDispatcher(config.getInnerConfig()[1]);
    }
}
