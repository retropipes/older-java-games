/*  TallerTower: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.tallertower.descriptionmanagers;

import java.io.IOException;

import com.puttysoftware.tallertower.TallerTower;
import com.puttysoftware.tallertower.creatures.faiths.FaithConstants;
import com.puttysoftware.tallertower.maze.Extension;
import com.puttysoftware.xio.ResourceStreamReader;

public class FaithDescriptionManager {
    public static String getFaithDescription(final int f) {
        final String name = FaithConstants.getFaithName(f).toLowerCase();
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                FaithDescriptionManager.class
                        .getResourceAsStream("/com/puttysoftware/tallertower/resources/descriptions/faith/"
                                + name
                                + Extension
                                        .getInternalDataExtensionWithPeriod()))) {
            // Fetch description
            final String desc = rsr.readString();
            return desc;
        } catch (final IOException e) {
            TallerTower.getErrorLogger().logError(e);
            return null;
        }
    }
}
