package org.ultra.validator.data.constraint;

import javax.script.ScriptException;
import java.util.HashMap;
import java.util.Map;

public class ConstraintDemo {
    public static void main(String[] args) {
        // 准备测试数据
        Map<String, Object> variables = new HashMap<>();
        variables.put("m",501);
        variables.put("n",1500);


        // 定义约束
        String expression3 = "(1 <= (m + n)) && ((m + n) <= 2000)";
        try {
            // 测试约束
            boolean result3 = ConstraintEvaluator.evaluate(expression3, variables);
            System.out.println("Result 3 (m + n <= 2000): " + result3);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }
}