package com.puttysoftware.randomrange;

public class RandomRange {
    // Fields
    private int minimum;
    private int maximum;

    // Constructor
    public RandomRange(int min, int max) {
        this.minimum = min;
        this.maximum = max;
    }

    // Methods
    public void setMinimum(int newMin) {
        this.minimum = newMin;
    }

    public void setMaximum(int newMax) {
        this.maximum = newMax;
    }

    public int generate() {
        if (this.maximum - this.minimum + 1 == 0) {
            return Math.abs(RandomnessSource.nextInt()) + this.minimum;
        } else {
            return Math.abs((RandomnessSource.nextInt())
                    % (this.maximum - this.minimum + 1))
                    + this.minimum;
        }
    }

    public static int generateRaw() {
        return RandomnessSource.nextInt();
    }
}
