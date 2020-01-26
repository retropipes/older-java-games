/*  TallerTower: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.tallertower.descriptionmanagers;

import java.io.IOException;

import com.puttysoftware.fileutils.ResourceStreamReader;
import com.puttysoftware.tallertower.TallerTower;
import com.puttysoftware.tallertower.creatures.races.RaceConstants;
import com.puttysoftware.tallertower.maze.Extension;

public class RaceDescriptionManager {
    public static String getRaceDescription(final int r) {
        final String name = RaceConstants.getRaceName(r).toLowerCase();
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                RaceDescriptionManager.class.getResourceAsStream(
                        "/com/puttysoftware/tallertower/resources/descriptions/race/"
                                + name + Extension
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
