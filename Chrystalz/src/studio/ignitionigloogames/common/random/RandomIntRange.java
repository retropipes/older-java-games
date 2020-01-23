package studio.ignitionigloogames.common.random;

public class RandomIntRange {
    // Constructor
    private RandomIntRange() {
        // Do nothing
    }

    // Methods
    public static int generate(final int min, final int max) {
        if (max - min + 1 == 0) {
            return Math.abs(RandomnessSource.nextInt()) + min;
        } else {
            return Math.abs(RandomnessSource.nextInt() % (max - min + 1)) + min;
        }
    }

    public static int generateRaw() {
        return RandomnessSource.nextInt();
    }
}
