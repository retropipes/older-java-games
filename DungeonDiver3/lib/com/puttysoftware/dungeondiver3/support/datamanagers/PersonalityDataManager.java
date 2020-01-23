/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.datamanagers;

import com.puttysoftware.dungeondiver3.support.creatures.personalities.PersonalityConstants;
import com.puttysoftware.dungeondiver3.support.scenario.Extension;
import com.puttysoftware.xio.ResourceStreamReader;

public class PersonalityDataManager {
    public static double[] getPersonalityData(final int p) {
        String name = PersonalityConstants.getPersonalityName(p).toLowerCase();
        try {
            // Fetch data
            final ResourceStreamReader rsr = new ResourceStreamReader(
                    PersonalityDataManager.class.getResourceAsStream("/com/puttysoftware/dungeondiver3/support/resources/data/personality/"
                            + name
                            + Extension.getPersonalityExtensionWithPeriod()));
            final int[] rawData = new int[PersonalityConstants.PERSONALITY_ATTRIBUTES_COUNT];
            for (int x = 0; x < rawData.length; x++) {
                rawData[x] = rsr.readInt();
            }
            rsr.close();
            // Parse raw data
            double[] finalData = new double[rawData.length];
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
