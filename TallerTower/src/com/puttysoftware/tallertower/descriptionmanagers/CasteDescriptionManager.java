/*  TallerTower: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.tallertower.descriptionmanagers;

import java.io.IOException;

import com.puttysoftware.tallertower.TallerTower;
import com.puttysoftware.tallertower.creatures.castes.CasteConstants;
import com.puttysoftware.tallertower.maze.Extension;
import com.puttysoftware.xio.ResourceStreamReader;

public class CasteDescriptionManager {
    public static String getCasteDescription(final int c) {
        final String name = CasteConstants.CASTE_NAMES[c].toLowerCase();
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                CasteDescriptionManager.class.getResourceAsStream(
                        "/com/puttysoftware/tallertower/resources/descriptions/caste/"
                                + name + Extension
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
