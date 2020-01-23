/*  MazeRunnerII: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.descriptionmanagers;

import com.puttysoftware.mazerunner2.creatures.faiths.FaithConstants;
import com.puttysoftware.mazerunner2.maze.Extension;
import com.puttysoftware.xio.ResourceStreamReader;

public class FaithDescriptionManager {
    public static String getFaithDescription(final int f) {
        String name = FaithConstants.getFaithName(f).toLowerCase();
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                FaithDescriptionManager.class
                        .getResourceAsStream("/com/puttysoftware/mazerunner2/resources/descriptions/faith/"
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
