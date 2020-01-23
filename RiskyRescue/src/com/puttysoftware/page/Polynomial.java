package com.puttysoftware.page;

import java.util.Arrays;

public class Polynomial {
    // Fields
    protected double[] coefficients;
    protected int max;

    // Constructors
    protected Polynomial(final Polynomial p) {
        this.coefficients = p.coefficients;
        this.max = p.max;
    }

    public Polynomial(final int maxPower) {
        this.coefficients = new double[maxPower + 1];
        this.max = maxPower;
    }

    // Methods
    public int getMaxPower() {
        return this.max;
    }

    public void setCoefficient(final int power, final double value) {
        this.coefficients[power] = value;
    }

    public long evaluate(final int paramValue) {
        int x;
        long result = 0;
        for (x = 0; x < this.coefficients.length; x++) {
            result += (long) (this.coefficients[x] * Math.pow(paramValue, x));
        }
        return result;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(this.coefficients);
        return prime * result + this.max;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Polynomial)) {
            return false;
        }
        final Polynomial other = (Polynomial) obj;
        if (!Arrays.equals(this.coefficients, other.coefficients)) {
            return false;
        }
        if (this.max != other.max) {
            return false;
        }
        return true;
    }
}
