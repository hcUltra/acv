package org.ultra.validator.data.constraint;

import org.ultra.validator.range.Range;

import javax.script.ScriptException;
import java.util.HashMap;
import java.util.Map;

public class ConstraintDemo {
    public static void main(String[] args) {
        // 准备测试数据
        Map<String, Object> variables = new HashMap<>();
        // 定义约束
        String expression1 = "0 <= m <= 1000";
        String expression2 = "0 <= n <= 1000";
        String expression3 = "1 <= m + n <= 2000";
        try {
            // 测试约束
            boolean result3 = ConstraintEvaluator.evaluate(expression3, variables);
            System.out.println("Result 3 (m + n <= 2000): " + result3);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }
}