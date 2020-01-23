/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.utilities;

import com.puttysoftware.randomrange.RandomRange;
import com.puttysoftware.riskyrescue.assets.data.NameDataManager;

public class PCNameGenerator {
    // Fields
    private static boolean NAMES_CACHED = false;
    private static String[] FAMILY_NAMES = null;
    private static RandomRange FAMILY_CHOOSER = null;
    private static String[] GIVEN_NAMES = null;
    private static RandomRange GIVEN_CHOOSER = null;

    // Private constructor
    private PCNameGenerator() {
        // Do nothing
    }

    // Methods
    public static String generate() {
        if (!PCNameGenerator.NAMES_CACHED) {
            PCNameGenerator.FAMILY_NAMES = NameDataManager.getFamilyNameData();
            PCNameGenerator.FAMILY_CHOOSER = new RandomRange(0,
                    PCNameGenerator.FAMILY_NAMES.length - 1);
            PCNameGenerator.GIVEN_NAMES = NameDataManager.getGivenNameData();
            PCNameGenerator.GIVEN_CHOOSER = new RandomRange(0,
                    PCNameGenerator.GIVEN_NAMES.length - 1);
            PCNameGenerator.NAMES_CACHED = true;
        }
        return PCNameGenerator.GIVEN_NAMES[PCNameGenerator.GIVEN_CHOOSER
                .generate()] + " "
                + PCNameGenerator.FAMILY_NAMES[PCNameGenerator.FAMILY_CHOOSER
                        .generate()];
    }
}
