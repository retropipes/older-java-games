/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.datamanagers;

import com.puttysoftware.fantastlex.creatures.faiths.FaithConstants;
import com.puttysoftware.fantastlex.maze.Extension;
import com.puttysoftware.xio.ResourceStreamReader;

public class FaithDataManager {
    public static double[] getFaithData(final int f) {
        final String name = FaithConstants.getFaithName(f).toLowerCase();
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                FaithDataManager.class
                        .getResourceAsStream("/com/puttysoftware/fantastlex/resources/data/faith/"
                                + name
                                + Extension
                                        .getInternalDataExtensionWithPeriod()))) {
            // Fetch data
            final int[] rawData = new int[FaithConstants.getFaithsCount()];
            for (int x = 0; x < rawData.length; x++) {
                rawData[x] = rsr.readInt();
            }
            // Parse raw data
            final double[] finalData = new double[rawData.length];
            for (int x = 0; x < rawData.length; x++) {
                finalData[x] = FaithConstants.getLookupTableEntry(rawData[x]);
            }
            return finalData;
        } catch (final Exception e) {
            return null;
        }
    }
}
