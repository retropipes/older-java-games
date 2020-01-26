/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.descriptionmanagers;

import com.puttysoftware.fantastlex.creatures.faiths.FaithConstants;
import com.puttysoftware.fantastlex.maze.Extension;
import com.puttysoftware.xio.ResourceStreamReader;

public class FaithDescriptionManager {
    public static String getFaithDescription(final int f) {
        final String name = FaithConstants.getFaithName(f).toLowerCase();
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                FaithDescriptionManager.class.getResourceAsStream(
                        "/com/puttysoftware/fantastlex/resources/descriptions/faith/"
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
