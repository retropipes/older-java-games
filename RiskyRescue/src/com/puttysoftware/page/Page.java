package com.puttysoftware.page;

public final class Page extends Polynomial {
    // Fields
    private int maxRange;
    private boolean experience;

    // Constructors
    private Page(final Polynomial p) {
        super(p);
    }

    public Page(final int maxPower, final int range,
            final boolean isExperience) {
        super(maxPower);
        this.maxRange = range;
        this.experience = isExperience;
    }

    // Methods
    public int getMaxRange() {
        return this.maxRange;
    }

    public boolean isExperience() {
        return this.experience;
    }

    @Override
    public long evaluate(final int paramValue) {
        int x;
        long result = 0;
        for (x = 0; x < this.coefficients.length; x++) {
            result += (long) (this.coefficients[x] * Math.pow(paramValue, x));
        }
        if (this.experience) {
            for (x = 0; x < this.coefficients.length; x++) {
                result -= (long) this.coefficients[x];
            }
        }
        return result;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (this.experience ? 1231 : 1237);
        return prime * result + this.maxRange;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof Page)) {
            return false;
        }
        final Page other = (Page) obj;
        if (this.experience != other.experience) {
            return false;
        }
        if (this.maxRange != other.maxRange) {
            return false;
        }
        return true;
    }
}
