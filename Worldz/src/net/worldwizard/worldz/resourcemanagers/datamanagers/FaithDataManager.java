/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell


Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.resourcemanagers.datamanagers;

import net.worldwizard.io.ResourceStreamReader;
import net.worldwizard.worldz.creatures.faiths.FaithConstants;

public class FaithDataManager {
    public static double[] getFaithData(final int f) {
        final String name = FaithConstants.FAITH_NAMES[f].toLowerCase();
        try {
            // Fetch data
            final ResourceStreamReader rsr = new ResourceStreamReader(
                    FaithDataManager.class
                            .getResourceAsStream("/net/worldwizard/worldz/resources/data/faith/"
                                    + name + ".dat"));
            final int[] rawData = new int[FaithConstants.FAITHS_COUNT];
            for (int x = 0; x < rawData.length; x++) {
                try {
                    rawData[x] = rsr.readInt();
                } catch (final NumberFormatException nfe) {
                    rawData[x] = -3;
                }
            }
            rsr.close();
            // Parse raw data
            final double[] finalData = new double[rawData.length];
            for (int x = 0; x < rawData.length; x++) {
                double d = 0.0;
                final int i = rawData[x];
                if (i == -2) {
                    d = 0.5;
                } else if (i == -1) {
                    d = 2.0 / 3.0;
                } else if (i == 1) {
                    d = 1.5;
                } else if (i == 2) {
                    d = 2.0;
                } else if (i == 0) {
                    d = 1.0;
                } else {
                    d = 0.0;
                }
                finalData[x] = d;
            }
            return finalData;
        } catch (final Exception e) {
            return null;
        }
    }
}
