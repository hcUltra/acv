package org.ultra.validator.data.constraint;

import java.util.Objects;

class AcvObj {
    int i, j, k;

    public AcvObj(int i, int j, int k) {
        this.i = i;
        this.j = j;
        this.k = k;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcvObj acvObj = (AcvObj) o;
        return i == acvObj.i && j == acvObj.j && k == acvObj.k;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j, k);
    }

    @Override
    public String toString() {
        return "acv[" + i + "][" + j + "][" + k + "]";
    }
}

