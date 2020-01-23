package net.worldwizard.randomnumbers;

import java.util.Random;

public class RandomRange {
    // Fields
    protected Random generator;
    protected long minimum;
    protected long maximum;

    // Constructor
    /**
     * 
     * @param min
     * @param max
     */
    public RandomRange(final long min, final long max) {
        this.generator = new Random();
        this.minimum = min;
        this.maximum = max;
    }

    // Methods
    /**
     * 
     * @return the random number generated
     */
    public int generate() {
        return (int) (Math.abs(this.generator.nextInt())
                % (this.maximum - this.minimum + 1) + this.minimum);
    }

    /**
     * 
     * @return the random number generated
     */
    public long generateLong() {
        return Math.abs(this.generator.nextLong())
                % (this.maximum - this.minimum + 1) + this.minimum;
    }
}
