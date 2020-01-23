/*  TallerTower: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.tallertower.descriptionmanagers;

import java.io.IOException;

import com.puttysoftware.tallertower.TallerTower;
import com.puttysoftware.tallertower.creatures.personalities.PersonalityConstants;
import com.puttysoftware.tallertower.maze.Extension;
import com.puttysoftware.xio.ResourceStreamReader;

public class PersonalityDescriptionManager {
    public static String getPersonalityDescription(final int p) {
        final String name = PersonalityConstants.getPersonalityName(p)
                .toLowerCase();
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                PersonalityDescriptionManager.class
                        .getResourceAsStream("/com/puttysoftware/tallertower/resources/descriptions/personality/"
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
