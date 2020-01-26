package net.dynamicdungeon.randomrange;

import java.util.Random;

class RandomnessSource {
    // Fields
    private static Random SOURCE = new Random();

    // Constructor
    private RandomnessSource() {
        // Do nothing
    }

    // Methods
    private static Random getSource() {
        return RandomnessSource.SOURCE;
    }

    static int nextInt() {
        return RandomnessSource.getSource().nextInt();
    }

    static long nextLong() {
        return RandomnessSource.getSource().nextLong();
    }
}
