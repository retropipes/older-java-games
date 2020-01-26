/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.descriptionmanagers;

import com.puttysoftware.dungeondiver3.support.creatures.faiths.FaithConstants;
import com.puttysoftware.dungeondiver3.support.scenario.Extension;
import com.puttysoftware.fileutils.ResourceStreamReader;

public class FaithDescriptionManager {
    public static String getFaithDescription(final int f) {
        final String name = FaithConstants.getFaithName(f).toLowerCase();
        try {
            // Fetch description
            final ResourceStreamReader rsr = new ResourceStreamReader(
                    FaithDescriptionManager.class.getResourceAsStream(
                            "/com/puttysoftware/dungeondiver3/support/resources/descriptions/faith/"
                                    + name + Extension
                                            .getDescriptionExtensionWithPeriod()));
            final String desc = rsr.readString();
            rsr.close();
            return desc;
        } catch (final Exception e) {
            return null;
        }
    }
}
