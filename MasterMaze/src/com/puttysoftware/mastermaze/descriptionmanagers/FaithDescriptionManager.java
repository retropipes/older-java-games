/*  MasterMaze: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.descriptionmanagers;

import com.puttysoftware.fileutils.ResourceStreamReader;
import com.puttysoftware.mastermaze.creatures.faiths.FaithConstants;
import com.puttysoftware.mastermaze.maze.Extension;

public class FaithDescriptionManager {
    public static String getFaithDescription(final int f) {
        final String name = FaithConstants.getFaithName(f).toLowerCase();
        try {
            // Fetch description
            final ResourceStreamReader rsr = new ResourceStreamReader(
                    FaithDescriptionManager.class.getResourceAsStream(
                            "/com/puttysoftware/mastermaze/resources/descriptions/faith/"
                                    + name + Extension
                                            .getInternalDataExtensionWithPeriod()));
            final String desc = rsr.readString();
            rsr.close();
            return desc;
        } catch (final Exception e) {
            return null;
        }
    }
}
