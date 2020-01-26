/*  MasterMaze: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.datamanagers;

import com.puttysoftware.mastermaze.creatures.personalities.PersonalityConstants;
import com.puttysoftware.mastermaze.maze.Extension;
import com.puttysoftware.xio.ResourceStreamReader;

public class PersonalityDataManager {
    public static double[] getPersonalityData(final int p) {
        final String name = PersonalityConstants.getPersonalityName(p)
                .toLowerCase();
        try {
            // Fetch data
            final ResourceStreamReader rsr = new ResourceStreamReader(
                    PersonalityDataManager.class.getResourceAsStream(
                            "/com/puttysoftware/mastermaze/resources/data/personality/"
                                    + name + Extension
                                            .getInternalDataExtensionWithPeriod()));
            final int[] rawData = new int[PersonalityConstants.PERSONALITY_ATTRIBUTES_COUNT];
            for (int x = 0; x < rawData.length; x++) {
                rawData[x] = rsr.readInt();
            }
            rsr.close();
            // Parse raw data
            final double[] finalData = new double[rawData.length];
            for (int x = 0; x < rawData.length; x++) {
                if (x == PersonalityConstants.PERSONALITY_ATTRIBUTE_LEVEL_UP_SPEED) {
                    finalData[x] = PersonalityConstants
                            .getLookupTableEntry(-rawData[x]);
                } else {
                    finalData[x] = PersonalityConstants
                            .getLookupTableEntry(rawData[x]);
                }
            }
            return finalData;
        } catch (final Exception e) {
            return null;
        }
    }
}
