package org.ultra.validator. core.parse;

import org.ultra.validator.common.util.SignatureUtil;
import org.ultra.validator. config.ArgumentConfig;
import org.ultra.validator.config.Pairs;
import org.ultra.validator. enums.ArgumentTypeEnum;
import org.ultra.validator. exception.UnableResolveTypeException;
import org.ultra.validator. exception.UnknownTypeException;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import static org.ultra.validator.core.parse.Parser.flatteningMap;

/**
 * @author yinger
 * @description TODO
 * @date 2024/12/30
 **/
public final class GenericArrayParser {
    public static void _fromGenericArray(ArgumentConfig config) throws UnableResolveTypeException, UnknownTypeException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, UnknownTypeException {
        config.setIThCollection(Parser.times[config.getDepth()]++);
        config.setArgumentType(ArgumentTypeEnum.ARRAY);
        Type type = config.getType();

        String fullClassNameStr = SignatureUtil.getGenericArraySignatureByType(type);
        config.setClassName(fullClassNameStr);

        if (config.getInnerConfig() == null || config.getInnerConfig().length == 0) {
            config.setInnerConfig(new ArgumentConfig[]{new ArgumentConfig()});
        }
        config.getInnerConfig()[0].setType(((GenericArrayType) type).getGenericComponentType());
        config.getInnerConfig()[0].setDepth(config.getDepth() + 1);
        config.getInnerConfig()[0].setIThArgument(config.getIThArgument());
        flatteningMap.put(new Pairs(config.getIThArgument(),config.getDepth(),config.getIThCollection()),config);
        Parser.parserDispatcher(config.getInnerConfig()[0]);
    }
}
