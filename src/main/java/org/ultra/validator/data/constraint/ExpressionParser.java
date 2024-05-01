package org.ultra.validator.data.constraint;

import org.ultra.validator.config.Pairs;
import org.ultra.validator.range.Range;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionParser {
    public static class Node {
        Pairs pairs;
        Range range;

        public Node(Pairs pairs, Range range) {
            this.pairs = pairs;
            this.range = range;
        }
    }

    public static Node parseExpression(String input) {
        // 匹配范围和固定值，包括size和value属性，支持负数
        String patternStr = "(-?\\d+)\\s*<=\\s*acv\\[(\\d+)\\]\\[(\\d+)\\]\\[(\\d+)\\]\\.(size|value)\\s*<=\\s*(-?\\d+)|" +
                "acv\\[(\\d+)\\]\\[(\\d+)\\]\\[(\\d+)\\]\\.(size|value)\\s*==\\s*(-?\\d+)";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            int i, j, k;
            String attribute;
            int lowerBound, upperBound, fixedValue;

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

            Pairs pairs = new Pairs(i, j, k);

            if (attribute.equals("size") || attribute.equals("value")) {
                Range range = new Range(attribute, lowerBound, upperBound);
                return new Node(pairs, range);
            } else {
                return null;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Map<Pairs, Range> map = new HashMap<>();
        ArrayList<String> constrains = new ArrayList<>();
        constrains.add("0 <= acv[0][0][0].size <= 1000");
        constrains.add("0 <= acv[1][0][0].size <= 1000");
        constrains.add("-10000000 <= acv[0][1][0].value <= 10000000");
        constrains.add("-10000000 <= acv[1][1][0].value <= 10000000");
        constrains.add("1 <= acv[0][0][0].size + acv[1][0][0].size <= 2000");

        for (String constraint : constrains) {
            Node node = parseExpression(constraint);
            if (node != null) {
                map.put(node.pairs, node.range);
            } else {
                // 需要计算的表达式
            }
        }
        map.forEach((key, value) -> System.out.println(key + ": " + value));
    }
}