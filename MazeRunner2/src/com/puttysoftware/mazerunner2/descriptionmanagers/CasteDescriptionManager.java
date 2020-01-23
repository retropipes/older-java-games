/*  MazeRunnerII: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.descriptionmanagers;

import com.puttysoftware.mazerunner2.creatures.castes.CasteConstants;
import com.puttysoftware.mazerunner2.maze.Extension;
import com.puttysoftware.xio.ResourceStreamReader;

public class CasteDescriptionManager {
    public static String getCasteDescription(final int c) {
        String name = CasteConstants.CASTE_NAMES[c].toLowerCase();
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                CasteDescriptionManager.class
                        .getResourceAsStream("/com/puttysoftware/mazerunner2/resources/descriptions/caste/"
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
