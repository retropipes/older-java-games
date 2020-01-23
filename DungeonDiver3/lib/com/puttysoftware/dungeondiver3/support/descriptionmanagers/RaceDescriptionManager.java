/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.descriptionmanagers;

import com.puttysoftware.dungeondiver3.support.creatures.races.RaceConstants;
import com.puttysoftware.dungeondiver3.support.scenario.Extension;
import com.puttysoftware.xio.ResourceStreamReader;

public class RaceDescriptionManager {
    public static String getRaceDescription(final int r) {
        String name = RaceConstants.getRaceName(r).toLowerCase();
        try {
            // Fetch description
            final ResourceStreamReader rsr = new ResourceStreamReader(
                    RaceDescriptionManager.class.getResourceAsStream("/com/puttysoftware/dungeondiver3/support/resources/descriptions/race/"
                            + name
                            + Extension.getDescriptionExtensionWithPeriod()));
            String desc = rsr.readString();
            rsr.close();
            return desc;
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
