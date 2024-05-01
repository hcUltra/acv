package org.ultra.validator.data.constraint;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ultra.validator.config.ArgumentConfig;
import org.ultra.validator.config.ArgumentsConfig;
import org.ultra.validator.config.Pairs;
import org.ultra.validator.core.parse.Parser;
import org.ultra.validator.range.Range;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class ExpressionParser {
    private static final Logger log = LoggerFactory.getLogger(ExpressionParser.class);

    public static class Node {
        Pairs pairs;
        Range range;

        public Node(Pairs pairs, Range range) {
            this.pairs = pairs;
            this.range = range;
        }
    }

    public static Node parseExpression(String input) {
        String patternStr = "(-?\\d+)\\s*<=\\s*((acv\\d{3}\\.(size|value)(\\s*\\+\\s*acv\\d{3}\\.(size|value))*)|acv\\d{3}\\.(size|value))\\s*<=\\s*(-?\\d+)|acv(\\d{3})\\.(size|value)\\s*==\\s*(-?\\d+)";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            if (matcher.group(2) != null) { // This handles both range and addition expressions
                int lowerBound = Integer.parseInt(matcher.group(1));
                String expression = matcher.group(2);
                int upperBound = Integer.parseInt(matcher.group(8));

                // Extracting multiple acv references from the expression
                Pattern acvPattern = Pattern.compile("(acv(\\d{3})\\.(size|value))");
                Matcher acvMatcher = acvPattern.matcher(expression);
                while (acvMatcher.find()) {
                    String attribute = acvMatcher.group(3);
                    int index = Integer.parseInt(acvMatcher.group(2));
                    int i = index / 100; // Assuming `i`, `j`, `k` are derived like this
                    int j = (index / 10) % 10;
                    int k = index % 10;
                    Pairs pairs = new Pairs(i, j, k);
                    Range range = new Range(attribute, lowerBound, upperBound);
                    return new Node(pairs, range); // This needs to be handled if multiple acv references exist
                }
            } else if (matcher.group(10) != null) {
                // Equality expression
                String attribute = matcher.group(12);
                int fixedValue = Integer.parseInt(matcher.group(13));
                int index = Integer.parseInt(matcher.group(11));
                int i = index / 100;
                int j = (index / 10) % 10;
                int k = index % 10;
                Pairs pairs = new Pairs(i, j, k);
                Range range = new Range(attribute, fixedValue, fixedValue);
                return new Node(pairs, range);
            }
        }
        return null;
    }

    public static void initConstraints(ArgumentsConfig argumentsConfig) {
        List<String> constraints = argumentsConfig.getConstrains();
        for (String constraint : constraints) {
            Node node = parseExpression(constraint);
            if (node != null) {
                Pairs pairs = node.pairs;
                Range range = node.range;
                ArgumentConfig ac = Parser.flatteningMap.get(pairs);
                if (constraint.contains("+")) {
                    // 需要计算的表达式，加入 argumentsConfig
                    argumentsConfig.getEvaluatorConstraints().add(constraint);
                } else if ("size".equals(range.getProperty())) {
                    ac.setSize(range);
                } else if ("value".equals(range.getProperty())) {
                    ac.setValue(range);
                } else {
                    log.error("range property {} error", range.getProperty());
                    return;
                }
            } else {
                // 需要计算的表达式，加入 argumentsConfig
                argumentsConfig.getEvaluatorConstraints().add(constraint);
            }
        }
    }

    public static void main(String[] args) {
        Map<Pairs, Range> map = new HashMap<>();
        ArrayList<String> constrains = new ArrayList<>();
        constrains.add("0 <= acv000.size <= 1000");
        constrains.add("0 <= acv100.size <= 1000");
        constrains.add("-10000000 <= acv010.value <= 10000000");
        constrains.add("-10000000 <= acv110.value <= 10000000");
        constrains.add("1 <= acv000.size + acv100.size <= 2000");

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