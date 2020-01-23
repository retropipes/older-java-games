package net.worldwizard.map;

import java.util.Random;

final class RandomRange {
    // Fields
    private final Random generator;
    private final int minRange;
    private final int maxRange;

    // Constructor
    RandomRange(final int min, final int max) {
        this.generator = new Random();
        this.minRange = min;
        this.maxRange = max;
    }

    // Method
    int generate() {
        return Math.abs(this.generator.nextInt())
                % (this.maxRange - this.minRange + 1) + this.minRange;
    }
}
