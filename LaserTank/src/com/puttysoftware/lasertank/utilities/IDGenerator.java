/*  LaserTank: An Arena-Solving Game
 Copyright (C) 2008-2013 Eric Ahnell

 Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.lasertank.utilities;

import com.puttysoftware.randomrange.RandomLongRange;

public class IDGenerator {
    // Constructor
    private IDGenerator() {
        // Do nothing
    }

    // Methods
    public static String generateRandomFilename() {
        return Long.toString(RandomLongRange.generateRaw(), 36).toLowerCase();
    }
}