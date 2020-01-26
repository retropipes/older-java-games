/*  DungeonDiver4: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.datamanagers;

import com.puttysoftware.dungeondiver4.creatures.races.RaceConstants;
import com.puttysoftware.dungeondiver4.dungeon.Extension;
import com.puttysoftware.xio.ResourceStreamReader;

public class RaceDataManager {
    public static int[] getRaceData(final int r) {
        final String name = RaceConstants.getRaceName(r).toLowerCase();
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                RaceDataManager.class.getResourceAsStream(
                        "/com/puttysoftware/dungeondiver4/resources/data/race/"
                                + name + Extension
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
