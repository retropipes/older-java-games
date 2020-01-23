/*  DungeonDiver4: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.datamanagers;

import com.puttysoftware.dungeondiver4.creatures.personalities.PersonalityConstants;
import com.puttysoftware.dungeondiver4.dungeon.Extension;
import com.puttysoftware.xio.ResourceStreamReader;

public class PersonalityDataManager {
    public static double[] getPersonalityData(final int p) {
        String name = PersonalityConstants.getPersonalityName(p).toLowerCase();
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                PersonalityDataManager.class
                        .getResourceAsStream("/com/puttysoftware/dungeondiver4/resources/data/personality/"
                                + name
                                + Extension
                                        .getInternalDataExtensionWithPeriod()))) {
            // Fetch data
            final int[] rawData = new int[PersonalityConstants.PERSONALITY_ATTRIBUTES_COUNT];
            for (int x = 0; x < rawData.length; x++) {
                rawData[x] = rsr.readInt();
            }
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
