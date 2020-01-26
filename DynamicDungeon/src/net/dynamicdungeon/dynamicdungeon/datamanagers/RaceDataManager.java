/*  DynamicDungeon: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.datamanagers;

import java.io.IOException;

import net.dynamicdungeon.dynamicdungeon.DynamicDungeon;
import net.dynamicdungeon.dynamicdungeon.creatures.races.RaceConstants;
import net.dynamicdungeon.dynamicdungeon.dungeon.Extension;
import net.dynamicdungeon.fileutils.ResourceStreamReader;

public class RaceDataManager {
    public static int[] getRaceData(final int r) {
        final String name = RaceConstants.getRaceName(r).toLowerCase();
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                RaceDataManager.class.getResourceAsStream(
                        "/net/dynamicdungeon/dynamicdungeon/resources/data/race/"
                                + name + Extension
                                        .getInternalDataExtensionWithPeriod()))) {
            // Fetch data
            final int[] rawData = new int[RaceConstants.RACE_ATTRIBUTE_COUNT];
            for (int x = 0; x < rawData.length; x++) {
                rawData[x] = rsr.readInt();
            }
            return rawData;
        } catch (final IOException e) {
            DynamicDungeon.getErrorLogger().logError(e);
            return null;
        }
    }
}
