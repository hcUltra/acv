package org.ultra.validator.data.constraint;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionParser {
    public static class Node {
        AcvObj acvObj;
        Range range;

        public Node(AcvObj acvObj, Range range) {
            this.acvObj = acvObj;
            this.range = range;
        }
    }

    public static Node parseExpression(String input) {
        Node node = null;
        // 匹配范围和固定值，包括size和value属性，支持负数
        String patternStr = "(-?\\d+)\\s*<=\\s*acv\\[(\\d+)\\]\\[(\\d+)\\]\\[(\\d+)\\]\\.(size|value)\\s*<=\\s*(-?\\d+)|" +
                "acv\\[(\\d+)\\]\\[(\\d+)\\]\\[(\\d+)\\]\\.(size|value)\\s*==\\s*(-?\\d+)";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            int i, j, k;
            String attribute;
            Integer lowerBound, upperBound, fixedValue;

            if (matcher.group(5) != null) {
                // Range expression
                lowerBound = Integer.parseInt(matcher.group(1));
                i = Integer.parseInt(matcher.group(2));
                j = Integer.parseInt(matcher.group(3));
                k = Integer.parseInt(matcher.group(4));
                attribute = matcher.group(5);
                upperBound = Integer.parseInt(matcher.group(6));
            } else {
                // Equality expression
                i = Integer.parseInt(matcher.group(7));
                j = Integer.parseInt(matcher.group(8));
                k = Integer.parseInt(matcher.group(9));
                attribute = matcher.group(10);
                fixedValue = Integer.parseInt(matcher.group(11));
                lowerBound = upperBound = fixedValue;
            }

            AcvObj acvObj = new AcvObj(i, j, k);

            if (attribute.equals("size") || attribute.equals("value")) {
                Range range = new Range(lowerBound, upperBound);
                return new Node(acvObj, range);
            } else {
                return null;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Map<AcvObj, Range> map = new HashMap<>();
        String ms = "acv[0][0][0].size == 1000";
        Node node1 = parseExpression(ms);
        if (node1 != null) {
            map.put(node1.acvObj, node1.range);
        }
        String ns = "0 <= acv[1][0][0].size <= 1000";
        Node node2 = parseExpression(ns);
        if (node2 != null) {
            map.put(node2.acvObj, node2.range);
        }
        String mv = "-10000000 <= acv[0][1][0].value <= 10000000";
        Node node3 = parseExpression(mv);
        if (node3 != null) {
            map.put(node3.acvObj, node3.range);
        }
        String nv = "-10000000 <= acv[1][1][0].value <= 10000000";
        Node node4 = parseExpression(nv);
        if (node4 != null) {
            map.put(node4.acvObj, node4.range);
        }
        String mnc = "1 <= acv[0][0][0].size + acv[1][0][0].size <= 2000";
        Node node5 = parseExpression(mnc);
        if (node5 != null) {
            map.put(node5.acvObj, node5.range);
        } else {
            // 计算表达式的真值

        }

        map.forEach((key, value) -> System.out.println(key + ": " + value));
    }
}
