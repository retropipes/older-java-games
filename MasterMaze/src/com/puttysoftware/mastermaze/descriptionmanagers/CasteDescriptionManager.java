/*  MasterMaze: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.descriptionmanagers;

import com.puttysoftware.mastermaze.creatures.castes.CasteConstants;
import com.puttysoftware.mastermaze.maze.Extension;
import com.puttysoftware.xio.ResourceStreamReader;

public class CasteDescriptionManager {
    public static String getCasteDescription(final int c) {
        final String name = CasteConstants.CASTE_NAMES[c].toLowerCase();
        try {
            // Fetch description
            final ResourceStreamReader rsr = new ResourceStreamReader(
                    CasteDescriptionManager.class.getResourceAsStream("/com/puttysoftware/mastermaze/resources/descriptions/caste/"
                            + name
                            + Extension.getInternalDataExtensionWithPeriod()));
            final String desc = rsr.readString();
            rsr.close();
            return desc;
        } catch (final Exception e) {
            return null;
        }
    }
}
