package org.ultra.validator.data.constraint;

import java.util.*;
import java.util.regex.*;

public class ConstraintSolver {
    // Map to hold variable range constraints
    private Map<String, int[]> rangeConstraints = new HashMap<>();
    // List to hold complex expressions
    private List<String> complexConstraints = new ArrayList<>();

    public void parseConstraints(List<String> constraints) {
        Pattern rangePattern = Pattern.compile("^(\\d+) <= (\\w+)( \\+ (\\w+))? <= (\\d+)$");

        for (String constraint : constraints) {
            Matcher matcher = rangePattern.matcher(constraint);
            if (matcher.find()) {
                int lowerBound = Integer.parseInt(matcher.group(1));
                int upperBound = Integer.parseInt(matcher.group(5));
                String firstVar = matcher.group(2);
                String secondVar = matcher.group(4);

                if (secondVar == null) {
                    // Simple range constraint
                    rangeConstraints.put(firstVar, new int[]{lowerBound, upperBound});
                } else {
                    // Complex expression, store as is for further processing
                    complexConstraints.add(constraint);
                }
            } else {
                System.out.println("Failed to parse constraint: " + constraint);
            }
        }
    }

    public Map<String, Integer> findValidValues() {
        Map<String, Integer> validValues = new HashMap<>();
        // Attempt to find a simple valid value within range for each variable
        for (Map.Entry<String, int[]> entry : rangeConstraints.entrySet()) {
            String variable = entry.getKey();
            int[] bounds = entry.getValue();
            // Simple middle value
            validValues.put(variable, (bounds[0] + bounds[1]) / 2);
        }

        // Here you would add logic to adjust values to satisfy complex constraints
        // This can get very complex and might need a smarter algorithm
        // For demonstration, just assume the simple case

        return validValues;
    }

    public static void main(String[] args) {
        List<String> constraints = new ArrayList<>();
        constraints.add("0 <= m <= 1500");
        constraints.add("0 <= n <= 1500");
        constraints.add("1 <= m + n <= 2000");

        ConstraintSolver solver = new ConstraintSolver();
        solver.parseConstraints(constraints);
        Map<String, Integer> validValues = solver.findValidValues();

        System.out.println("Valid values:");
        for (Map.Entry<String, Integer> entry : validValues.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }
}
