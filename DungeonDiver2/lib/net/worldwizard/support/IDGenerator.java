package net.worldwizard.support;

import java.math.BigInteger;

import net.worldwizard.randomnumbers.RandomRange;

public class IDGenerator {
    // Constants
    private static final long BOOLEAN_TRUE = 109;
    private static final long BOOLEAN_FALSE = 101;

    // Constructor
    private IDGenerator() {
        // Do nothing
    }

    // Methods
    public static String generateRandomID() {
        final RandomRange r = new RandomRange(Long.MIN_VALUE, Long.MAX_VALUE);
        return Long.toString(r.generateRawLong(), 36).toUpperCase();
    }

    public static BigInteger computeStringLongHash(final String s) {
        BigInteger lHash = BigInteger.ZERO;
        if (s != null) {
            final char[] raw = s.toCharArray();
            for (int x = 0; x < raw.length; x++) {
                final BigInteger intermed = BigInteger
                        .valueOf((x + 1) * raw[x]);
                lHash = lHash.add(intermed);
            }
        }
        return lHash;
    }

    public static BigInteger computeLongLongHash(final long l) {
        return BigInteger.valueOf(l);
    }

    public static BigInteger computeDoubleLongHash(final double d) {
        return BigInteger.valueOf(Double.doubleToLongBits(d));
    }

    public static BigInteger computeBooleanLongHash(final boolean b) {
        if (b) {
            return BigInteger.valueOf(IDGenerator.BOOLEAN_TRUE);
        } else {
            return BigInteger.valueOf(IDGenerator.BOOLEAN_FALSE);
        }
    }
}