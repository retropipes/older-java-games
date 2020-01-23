/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell


 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.datamanagers;

import com.puttysoftware.fileutils.ResourceStreamReader;
import com.puttysoftware.gemma.support.creatures.personalities.PersonalityConstants;
import com.puttysoftware.gemma.support.scenario.Extension;

public class PersonalityDataManager {
    public static double[] getPersonalityData(final int p) {
        String name = PersonalityConstants.getPersonalityName(p).toLowerCase();
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                PersonalityDataManager.class.getResourceAsStream(
                        "/com/puttysoftware/gemma/support/resources/data/personality/"
                                + name + Extension
                                        .getPersonalityExtensionWithPeriod()))) {
            // Fetch data
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
