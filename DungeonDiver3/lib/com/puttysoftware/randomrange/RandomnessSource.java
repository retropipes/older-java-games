package com.puttysoftware.randomrange;

import java.util.Random;

class RandomnessSource {
    // Fields
    private static Random SOURCE = null;

    // Constructor
    private RandomnessSource() {
        // Do nothing
    }

    // Methods
    private static Random getSource() {
        if (SOURCE == null) {
            SOURCE = new Random();
        }
        return SOURCE;
    }

    static long nextLong() {
        return getSource().nextLong();
    }

    static double nextDouble() {
        return getSource().nextDouble();
    }
}
