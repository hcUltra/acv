package org.ultra.validator.config;

import java.util.Objects;

/**
 * @author hcUltra
 * @description
 * @date 2024/4/30 21:35
 **/
public class Pairs {
    public int i;
    public int j;
    public int k;

    public Pairs(int i, int j, int k) {
        this.i = i;
        this.j = j;
        this.k = k;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pairs pairs = (Pairs) o;
        return i == pairs.i && j == pairs.j && k == pairs.k;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j, k);
    }
}
