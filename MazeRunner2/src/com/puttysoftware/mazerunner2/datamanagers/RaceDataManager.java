/*  MazeRunnerII: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.datamanagers;

import com.puttysoftware.mazerunner2.creatures.races.RaceConstants;
import com.puttysoftware.mazerunner2.maze.Extension;
import com.puttysoftware.xio.ResourceStreamReader;

public class RaceDataManager {
    public static int[] getRaceData(final int r) {
        String name = RaceConstants.getRaceName(r).toLowerCase();
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                RaceDataManager.class
                        .getResourceAsStream("/com/puttysoftware/mazerunner2/resources/data/race/"
                                + name
                                + Extension
                                        .getInternalDataExtensionWithPeriod()))) {
            // Fetch data
            final int[] rawData = new int[RaceConstants.RACE_ATTRIBUTE_COUNT];
            for (int x = 0; x < rawData.length; x++) {
                rawData[x] = rsr.readInt();
            }
            return rawData;
        } catch (final Exception e) {
            return null;
        }
    }
}
