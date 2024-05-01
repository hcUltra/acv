package org.ultra.validator.data.constraint;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Constraint {
    private String expression;  // 表达式, 如 "m + n <= 2000"
}
