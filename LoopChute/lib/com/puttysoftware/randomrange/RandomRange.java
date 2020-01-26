package com.puttysoftware.randomrange;

public class RandomRange {
    // Fields
    private long minimum;
    private long maximum;

    // Constructor
    public RandomRange(final long min, final long max) {
        this.minimum = min;
        this.maximum = max;
    }

    // Methods
    public void setMinimum(final long newMin) {
        this.minimum = newMin;
    }

    public void setMaximum(final long newMax) {
        this.maximum = newMax;
    }

    public int generate() {
        return (int) this.generateLong();
    }

    public long generateLong() {
        return Math.abs(
                RandomnessSource.nextLong() % (this.maximum - this.minimum + 1))
                + this.minimum;
    }

    public static long generateRawLong() {
        return RandomnessSource.nextLong();
    }
}
