/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.datamanagers;

import net.worldwizard.support.creatures.faiths.FaithConstants;
import net.worldwizard.xio.ResourceStreamReader;

public class FaithDataManager {
    private static final int ADD_FACTOR = 4;
    private static final double DIVIDE_FACTOR = 4.0;

    public static double[] getFaithData(final int f) {
        final String name = FaithConstants.getFaithNames()[f].toLowerCase();
        try {
            // Fetch data
            final ResourceStreamReader rsr = new ResourceStreamReader(
                    FaithDataManager.class
                            .getResourceAsStream("/net/worldwizard/support/resources/data/faith/"
                                    + name + ".dat"));
            final int[] rawData = new int[FaithConstants.getFaithsCount()];
            for (int x = 0; x < rawData.length; x++) {
                try {
                    rawData[x] = rsr.readInt();
                } catch (final NumberFormatException nfe) {
                    rawData[x] = -FaithDataManager.ADD_FACTOR;
                }
            }
            rsr.close();
            // Parse raw data
            final double[] finalData = new double[rawData.length];
            for (int x = 0; x < rawData.length; x++) {
                final int i = rawData[x] + FaithDataManager.ADD_FACTOR;
                final double d = i / FaithDataManager.DIVIDE_FACTOR;
                finalData[x] = d;
            }
            return finalData;
        } catch (final Exception e) {
            return null;
        }
    }
}
