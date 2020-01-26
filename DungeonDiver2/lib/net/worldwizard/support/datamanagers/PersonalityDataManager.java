/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.datamanagers;

import net.worldwizard.support.creatures.personalities.PersonalityConstants;
import net.worldwizard.xio.ResourceStreamReader;

public class PersonalityDataManager {
    public static int[] getPersonalityData(final int p) {
        final String name = PersonalityConstants.PERSONALITY_NAMES[p]
                .toLowerCase();
        try {
            // Fetch data
            final ResourceStreamReader rsr = new ResourceStreamReader(
                    PersonalityDataManager.class.getResourceAsStream(
                            "/net/worldwizard/support/resources/data/personality/"
                                    + name + ".dat"));
            final int[] rawData = new int[PersonalityConstants.PERSONALITY_ATTRIBUTE_COUNT];
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