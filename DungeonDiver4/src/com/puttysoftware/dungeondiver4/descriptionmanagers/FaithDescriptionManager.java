/*  DungeonDiver4: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.descriptionmanagers;

import com.puttysoftware.dungeondiver4.creatures.faiths.FaithConstants;
import com.puttysoftware.dungeondiver4.dungeon.Extension;
import com.puttysoftware.fileutils.ResourceStreamReader;

public class FaithDescriptionManager {
    public static String getFaithDescription(final int f) {
        final String name = FaithConstants.getFaithName(f).toLowerCase();
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                FaithDescriptionManager.class.getResourceAsStream(
                        "/com/puttysoftware/dungeondiver4/resources/descriptions/faith/"
                                + name + Extension
                                        .getInternalDataExtensionWithPeriod()))) {
            // Fetch description
            final String desc = rsr.readString();
            return desc;
        } catch (final Exception e) {
            return null;
        }
    }
}
