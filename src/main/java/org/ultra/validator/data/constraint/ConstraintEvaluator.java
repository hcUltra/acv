package org.ultra.validator.data.constraint;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Map;
public class ConstraintEvaluator {
    private static final ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");

    // 根据提供的变量值计算表达式
    public static boolean evaluate(String expression, Map<String, Object> variables) throws ScriptException {
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            engine.put(entry.getKey(), entry.getValue());
        }
        return (Boolean) engine.eval(expression);
    }
}
