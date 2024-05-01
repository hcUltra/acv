package org.ultra.validator.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.ultra.validator.annotation.Validator;
import org.ultra.validator.common.util.ReflectUtil;
import org.ultra.validator.config.ArgumentsConfig;
import org.ultra.validator.config.Collation;
import org.ultra.validator.core.parse.Parser;
import org.ultra.validator.data.constraint.ExpressionParser;
import org.ultra.validator.exception.UnableResolveTypeException;

import javax.script.ScriptException;
import java.lang.reflect.InvocationTargetException;

@Slf4j
public class Active {
    /**
     * 激活验证器
     *
     * @throws IllegalArgumentException 如果 arguments 为 null
     */
    public void activateValidator(ArgumentsConfig argumentsConfig) {
        try {
            if (argumentsConfig == null) {
                throw new IllegalArgumentException("ArgumentsConfig is null!");
            }
            // 反射获取 Solution 类 注解的参数
            Class<?> clazz = ArgumentsConfig.clazz;
            Validator configAnnotation = clazz.getAnnotation(Validator.class);
            argumentsConfig.setTestTimes(configAnnotation.count());
            argumentsConfig.setValidatorMethod(ReflectUtil.reflectValidatorAnnotationMethod());
            argumentsConfig.setCorrectMethod(ReflectUtil.reflectCorretAnnotationMethod());
            // 收集参数信息 并对其进行扁平化处理 设置模板
            Parser.preParser(argumentsConfig);
            // 初始化约束
            ExpressionParser.initConstraints(argumentsConfig);
//            argumentsConfig.getArgumentConfigs().get(0).setCollation(new Collation());
//            argumentsConfig.getArgumentConfigs().get(1).setCollation(new Collation());
            // 启动验证过程
            boolean result = verifyArguments(argumentsConfig);
            // 记录验证结果日志
            if (result) {
                log.info("test passed!");
            } else {
                log.error("test failed!");
            }
        } catch (Exception e) {
            log.error("test failed,take exception!", e);
        }
    }

    private boolean verifyArguments(ArgumentsConfig argumentsConfig) throws JsonProcessingException, UnableResolveTypeException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException, ScriptException {
        return new org.ultra.validator.process.Validator().verification(argumentsConfig);
    }
}
