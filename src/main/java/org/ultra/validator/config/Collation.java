package org.ultra.validator.config;

import lombok.Data;

import java.util.Comparator;

/**
 * @author hcUltra
 * @description TODO
 * @date 2024/4/24 07:34
 **/
@Data
public class Collation {
    private Boolean isSorted = true;
    // 默认正序
    private Comparator comparator = Comparator.naturalOrder();
    public Collation(Boolean isSorted) {
        this.isSorted = isSorted;
    }
    public Collation(Boolean isSorted, Comparator comparator) {
        this.isSorted = isSorted;
        this.comparator = comparator;
    }

    public Collation() {
    }
}
