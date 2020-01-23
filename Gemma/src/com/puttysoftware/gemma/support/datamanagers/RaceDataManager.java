/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell


 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.datamanagers;

import com.puttysoftware.fileutils.ResourceStreamReader;
import com.puttysoftware.gemma.support.creatures.races.RaceConstants;
import com.puttysoftware.gemma.support.scenario.Extension;

public class RaceDataManager {
    public static int[] getRaceData(final int r) {
        String name = RaceConstants.getRaceName(r).toLowerCase();
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                RaceDataManager.class.getResourceAsStream(
                        "/com/puttysoftware/gemma/support/resources/data/race/"
                                + name
                                + Extension.getRaceExtensionWithPeriod()))) {
            // Fetch data
            final int[] rawData = new int[RaceConstants.RACE_ATTRIBUTE_COUNT];
            for (int x = 0; x < rawData.length; x++) {
                rawData[x] = rsr.readInt();
            }
            rsr.close();
            return rawData;
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
