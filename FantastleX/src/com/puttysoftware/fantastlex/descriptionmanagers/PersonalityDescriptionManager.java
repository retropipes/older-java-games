/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.descriptionmanagers;

import com.puttysoftware.fantastlex.creatures.personalities.PersonalityConstants;
import com.puttysoftware.fantastlex.maze.Extension;
import com.puttysoftware.xio.ResourceStreamReader;

public class PersonalityDescriptionManager {
    public static String getPersonalityDescription(final int p) {
        final String name = PersonalityConstants.getPersonalityName(p)
                .toLowerCase();
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                PersonalityDescriptionManager.class.getResourceAsStream(
                        "/com/puttysoftware/fantastlex/resources/descriptions/personality/"
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
