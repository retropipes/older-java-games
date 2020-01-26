/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.descriptionmanagers;

import com.puttysoftware.fantastlex.creatures.races.RaceConstants;
import com.puttysoftware.fantastlex.maze.Extension;
import com.puttysoftware.xio.ResourceStreamReader;

public class RaceDescriptionManager {
    public static String getRaceDescription(final int r) {
        final String name = RaceConstants.getRaceName(r).toLowerCase();
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                RaceDescriptionManager.class.getResourceAsStream(
                        "/com/puttysoftware/fantastlex/resources/descriptions/race/"
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
