/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.descriptionmanagers;

import com.puttysoftware.dungeondiver3.support.creatures.personalities.PersonalityConstants;
import com.puttysoftware.dungeondiver3.support.scenario.Extension;
import com.puttysoftware.xio.ResourceStreamReader;

public class PersonalityDescriptionManager {
    public static String getPersonalityDescription(final int p) {
        final String name = PersonalityConstants.getPersonalityName(p)
                .toLowerCase();
        try {
            // Fetch description
            final ResourceStreamReader rsr = new ResourceStreamReader(
                    PersonalityDescriptionManager.class.getResourceAsStream(
                            "/com/puttysoftware/dungeondiver3/support/resources/descriptions/personality/"
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
