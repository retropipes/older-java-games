/*  MasterMaze: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.descriptionmanagers;

import com.puttysoftware.fileutils.ResourceStreamReader;
import com.puttysoftware.mastermaze.creatures.personalities.PersonalityConstants;
import com.puttysoftware.mastermaze.maze.Extension;

public class PersonalityDescriptionManager {
    public static String getPersonalityDescription(final int p) {
        final String name = PersonalityConstants.getPersonalityName(p)
                .toLowerCase();
        try {
            // Fetch description
            final ResourceStreamReader rsr = new ResourceStreamReader(
                    PersonalityDescriptionManager.class.getResourceAsStream(
                            "/com/puttysoftware/mastermaze/resources/descriptions/personality/"
                                    + name + Extension
                                            .getInternalDataExtensionWithPeriod()));
            final String desc = rsr.readString();
            rsr.close();
            return desc;
        } catch (final Exception e) {
            return null;
        }
    }
}
