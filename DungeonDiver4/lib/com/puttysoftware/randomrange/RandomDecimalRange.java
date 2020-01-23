package com.puttysoftware.randomrange;

public class RandomDecimalRange {
    // Fields
    private static double minimum;
    private static double maximum;

    // Constructor
    private RandomDecimalRange() {
        // Do nothing
    }

    // Methods
    public static void setMinimum(double newMin) {
        minimum = newMin;
    }

    public static void setMaximum(double newMax) {
        maximum = newMax;
    }

    public static float generateFloat() {
        return (float) generateDouble();
    }

    public static double generateDouble() {
        return Math.abs((RandomnessSource.nextDouble())
                % (maximum - minimum + 1))
                + minimum;
    }

    public static double generateRawDouble() {
        return RandomnessSource.nextDouble();
    }
}
