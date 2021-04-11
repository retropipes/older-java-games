package studio.ignitionigloogames.dungeondiver1.utilities;

import java.util.Random;

public class RandomRange {
    // Fields
    protected Random generator;
    protected int minRange;
    protected int maxRange;

    // Constructor
    public RandomRange(final int min, final int max) {
        this.generator = new Random();
        this.minRange = min;
        this.maxRange = max;
    }

    // Method
    public int generate() {
        return Math.abs(this.generator.nextInt())
                % (this.maxRange - this.minRange + 1) + this.minRange;
    }
}
