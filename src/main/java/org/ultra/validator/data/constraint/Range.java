package org.ultra.validator.data.constraint;

public class Range {
    Integer sizeLower;
    Integer sizeUpper;
    Integer valueLower;
    Integer valueUpper;

    public Range(Integer sizeLower, Integer sizeUpper) {
        this.sizeLower = sizeLower;
        this.sizeUpper = sizeUpper;
    }

    public Range(Integer sizeLower, Integer sizeUpper, Integer valueLower, Integer valueUpper) {
        this.sizeLower = sizeLower;
        this.sizeUpper = sizeUpper;
        this.valueLower = valueLower;
        this.valueUpper = valueUpper;
    }

    @Override
    public String toString() {
        return "Range{size=" + "(" + sizeLower + "," + sizeUpper + ")" +
                ", value=" + (valueLower != null ? valueLower + "-" + valueUpper : "N/A") + '}';
    }
}
