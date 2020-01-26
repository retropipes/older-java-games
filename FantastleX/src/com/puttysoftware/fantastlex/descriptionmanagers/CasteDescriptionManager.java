/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.descriptionmanagers;

import com.puttysoftware.fantastlex.creatures.castes.CasteConstants;
import com.puttysoftware.fantastlex.maze.Extension;
import com.puttysoftware.xio.ResourceStreamReader;

public class CasteDescriptionManager {
    public static String getCasteDescription(final int c) {
        final String name = CasteConstants.CASTE_NAMES[c].toLowerCase();
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                CasteDescriptionManager.class.getResourceAsStream(
                        "/com/puttysoftware/fantastlex/resources/descriptions/caste/"
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
