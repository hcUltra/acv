package org.ultra.validator.process;

import lombok.extern.slf4j.Slf4j;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.ultra.validator.common.util.ReflectUtil;
import org.ultra.validator.config.ArgumentsConfig;
import org.ultra.validator.core.construct.RandomArgumentConstructor;
import org.ultra.validator.core.parse.Parser;
import org.ultra.validator.data.DeepEquals;
import org.ultra.validator.data.constraint.ConstraintEvaluator;
import org.ultra.validator.exception.UnableResolveTypeException;
import org.ultra.validator.range.Range;

import javax.script.ScriptException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yinger
 * @description 验证器
 * @date 2024/1/4 19:35
 **/
@Slf4j
public class Validator {
    private static final int MAX_VERIFICATION_TIMES = 10_0000;

    /**
     * 验证函数
     *
     * @param config 验证器配置对象
     * @return 验证结果，true表示验证通过，false表示验证不通过
     * @throws IllegalArgumentException 如果配置不正确抛出异常
     */
    protected boolean verification(ArgumentsConfig config) throws IllegalArgumentException, JsonProcessingException, UnableResolveTypeException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException, ScriptException {
        if (config.getTestTimes() > MAX_VERIFICATION_TIMES) {
            throw new IllegalArgumentException("配置的验证次数过多。");
        }
        long validatorTakeTime = 0;
        long correctTakeTime = 0;
        // TODO before
        for (int i = 0; i < config.getTestTimes(); i++) {
            // TODO 设置本次随机的各集合的 size
            int n = config.getEvaluatorConstraints().size();
            for (int j = 0; j < n; j++) {
                Map<String, Object> constrains = new HashMap<>();
                boolean ok = false;
                do {
                    Parser.flatteningMap.forEach((key, value) -> {
                        if (value.getSize() != null) {
                            value.setFixSize((int) Range.getNumberMinToMax(value.getSize()));
                            constrains.put("acv" + key.i + key.j + key.k, (int) Range.getNumberMinToMax(value.getSize()));
                        }
                    });
                    ok = ConstraintEvaluator.evaluate(config.getEvaluatorConstraints().get(j).replace(".size", ""), constrains);
                } while (!ok);
            }
            Object[][] test = RandomArgumentConstructor.constructDispatcher(config);
            // TODO after
            long startValidatorTime = System.currentTimeMillis();
            Object result1 = ReflectUtil.invoke(config.getValidatorMethod(), test[0]);
            validatorTakeTime += System.currentTimeMillis() - startValidatorTime;

            long startCorrectTime = System.currentTimeMillis();
            Object result2 = ReflectUtil.invoke(config.getCorrectMethod(), test[1]);
            correctTakeTime += System.currentTimeMillis() - startCorrectTime;
            if (!DeepEquals.deepEquals(result1, result2)) {
                System.out.println(Arrays.deepToString(test[0]));
                System.out.println(Arrays.deepToString(test[1]));
                log.error("验证第{}次失败", i + 1);
                return false;
            }
            log.info("第{}次验证通过", i + 1);
        }
        log.info("validator method take time: {} ms", validatorTakeTime);
        log.info("correct method take time: {} ms", correctTakeTime);
        return true;
    }

}
