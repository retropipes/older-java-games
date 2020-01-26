/*  DDRemix: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.ddremix.datamanagers;

import java.io.IOException;

import com.puttysoftware.ddremix.DDRemix;
import com.puttysoftware.ddremix.creatures.races.RaceConstants;
import com.puttysoftware.ddremix.maze.Extension;
import com.puttysoftware.fileutils.ResourceStreamReader;

public class RaceDataManager {
    public static int[] getRaceData(final int r) {
        final String name = RaceConstants.getRaceName(r).toLowerCase();
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                RaceDataManager.class.getResourceAsStream(
                        "/com/puttysoftware/ddremix/resources/data/race/" + name
                                + Extension
                                        .getInternalDataExtensionWithPeriod()))) {
            // Fetch data
            final int[] rawData = new int[RaceConstants.RACE_ATTRIBUTE_COUNT];
            for (int x = 0; x < rawData.length; x++) {
                rawData[x] = rsr.readInt();
            }
            return rawData;
        } catch (final IOException e) {
            DDRemix.getErrorLogger().logError(e);
            return null;
        }
    }
}
