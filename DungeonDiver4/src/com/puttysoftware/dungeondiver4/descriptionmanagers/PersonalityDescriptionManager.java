/*  DungeonDiver4: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.descriptionmanagers;

import com.puttysoftware.dungeondiver4.creatures.personalities.PersonalityConstants;
import com.puttysoftware.dungeondiver4.dungeon.Extension;
import com.puttysoftware.xio.ResourceStreamReader;

public class PersonalityDescriptionManager {
    public static String getPersonalityDescription(final int p) {
        final String name = PersonalityConstants.getPersonalityName(p)
                .toLowerCase();
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                PersonalityDescriptionManager.class.getResourceAsStream(
                        "/com/puttysoftware/dungeondiver4/resources/descriptions/personality/"
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
