package com.puttysoftware.gemma.support.effects;

public class Fraction {
    // Fields
    private int numerator;
    private int denominator;

    // Constructors
    public Fraction() {
        this.numerator = 1;
        this.denominator = 1;
    }

    public Fraction(final int newNumerator, final int newDenominator) {
        this.numerator = newNumerator;
        this.denominator = newDenominator;
    }

    // Methods
    public int getNumerator() {
        return this.numerator;
    }

    public void setNumerator(int newNumerator) {
        this.numerator = newNumerator;
    }

    public int getDenominator() {
        return this.denominator;
    }

    public void setDenominator(int newDenominator) {
        this.denominator = newDenominator;
    }

    public Fraction add(final Fraction other) {
        int newDenom = this.denominator;
        int newNum = this.numerator + other.numerator;
        if (this.denominator != other.denominator) {
            newDenom = this.denominator * other.denominator;
            newNum = (this.numerator * other.denominator)
                    + (other.numerator * other.denominator);
        }
        return new Fraction(newNum, newDenom);
    }

    public Fraction multiply(final Fraction other) {
        int newDenom = this.denominator * other.denominator;
        int newNum = this.numerator * other.numerator;
        return new Fraction(newNum, newDenom);
    }

    public double toDouble() {
        return (double) this.numerator / (double) this.denominator;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.numerator;
        hash = 67 * hash + this.denominator;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Fraction other = (Fraction) obj;
        if (this.numerator != other.numerator) {
            return false;
        }
        if (this.denominator != other.denominator) {
            return false;
        }
        return true;
    }
}
