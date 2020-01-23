/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.descriptionmanagers;

import com.puttysoftware.dungeondiver3.support.creatures.castes.CasteConstants;
import com.puttysoftware.dungeondiver3.support.scenario.Extension;
import com.puttysoftware.xio.ResourceStreamReader;

public class CasteDescriptionManager {
    public static String getCasteDescription(final int c) {
        String name = CasteConstants.CASTE_NAMES[c].toLowerCase();
        try {
            // Fetch description
            final ResourceStreamReader rsr = new ResourceStreamReader(
                    CasteDescriptionManager.class.getResourceAsStream("/com/puttysoftware/dungeondiver3/support/resources/descriptions/caste/"
                            + name
                            + Extension.getDescriptionExtensionWithPeriod()));
            String desc = rsr.readString();
            rsr.close();
            return desc;
        } catch (final Exception e) {
            return null;
        }
    }
}
