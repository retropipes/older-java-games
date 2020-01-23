/*  MazeRunnerII: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.descriptionmanagers;

import com.puttysoftware.mazerunner2.creatures.personalities.PersonalityConstants;
import com.puttysoftware.mazerunner2.maze.Extension;
import com.puttysoftware.xio.ResourceStreamReader;

public class PersonalityDescriptionManager {
    public static String getPersonalityDescription(final int p) {
        String name = PersonalityConstants.getPersonalityName(p).toLowerCase();
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                PersonalityDescriptionManager.class
                        .getResourceAsStream("/com/puttysoftware/mazerunner2/resources/descriptions/personality/"
                                + name
                                + Extension
                                        .getInternalDataExtensionWithPeriod()))) {
            // Fetch description
            String desc = rsr.readString();
            return desc;
        } catch (final Exception e) {
            return null;
        }
    }
}
