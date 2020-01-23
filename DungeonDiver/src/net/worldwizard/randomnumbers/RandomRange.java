package net.worldwizard.randomnumbers;

import java.util.Random;

public class RandomRange {
    // Fields
    protected Random generator;
    protected long minRange;
    protected long maxRange;

    // Constructor
    public RandomRange(final long min, final long max) {
        this.generator = new Random();
        this.minRange = min;
        this.maxRange = max;
    }

    // Method
    public long generate() {
        return Math.abs(this.generator.nextLong())
                % (this.maxRange - this.minRange + 1) + this.minRange;
    }
}
