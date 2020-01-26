/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell


Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.resourcemanagers.datamanagers;

import net.worldwizard.io.ResourceStreamReader;
import net.worldwizard.worldz.creatures.genders.GenderConstants;

public class GenderDataManager {
    public static int[] getGenderData(final int g) {
        final String name = GenderConstants.GENDER_NAMES[g].toLowerCase();
        try {
            // Fetch data
            final ResourceStreamReader rsr = new ResourceStreamReader(
                    GenderDataManager.class.getResourceAsStream(
                            "/net/worldwizard/worldz/resources/data/gender/"
                                    + name + ".dat"));
            final int[] rawData = new int[GenderConstants.GENDERS_ATTRIBUTE_COUNT];
            for (int x = 0; x < rawData.length; x++) {
                try {
                    rawData[x] = rsr.readInt();
                } catch (final NumberFormatException nfe) {
                    rawData[x] = 0;
                }
            }
            rsr.close();
            return rawData;
        } catch (final Exception e) {
            return null;
        }
    }
}
