/*  MasterMaze: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.descriptionmanagers;

import com.puttysoftware.fileutils.ResourceStreamReader;
import com.puttysoftware.mastermaze.creatures.races.RaceConstants;
import com.puttysoftware.mastermaze.maze.Extension;

public class RaceDescriptionManager {
    public static String getRaceDescription(final int r) {
        final String name = RaceConstants.getRaceName(r).toLowerCase();
        try {
            // Fetch description
            final ResourceStreamReader rsr = new ResourceStreamReader(
                    RaceDescriptionManager.class.getResourceAsStream(
                            "/com/puttysoftware/mastermaze/resources/descriptions/race/"
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
