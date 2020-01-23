package com.puttysoftware.randomrange;

public class RandomLongRange {
    // Fields
    private long minimum;
    private long maximum;

    // Constructor
    public RandomLongRange(long min, long max) {
        this.minimum = min;
        this.maximum = max;
    }

    // Methods
    public void setMinimum(long newMin) {
        this.minimum = newMin;
    }

    public void setMaximum(long newMax) {
        this.maximum = newMax;
    }

    public long generate() {
        if (this.maximum - this.minimum + 1 == 0) {
            return Math.abs(RandomnessSource.nextLong()) + this.minimum;
        } else {
            return Math.abs((RandomnessSource.nextLong())
                    % (this.maximum - this.minimum + 1))
                    + this.minimum;
        }
    }

    public static long generateRaw() {
        return RandomnessSource.nextLong();
    }
}
