/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell


 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.datamanagers;

import com.puttysoftware.fileutils.ResourceStreamReader;
import com.puttysoftware.gemma.support.creatures.faiths.FaithConstants;
import com.puttysoftware.gemma.support.scenario.Extension;

public class FaithDataManager {
    public static double[] getFaithData(final int f) {
        final String name = FaithConstants.getFaithName(f).toLowerCase();
        try (final ResourceStreamReader rsr = new ResourceStreamReader(
                FaithDataManager.class.getResourceAsStream(
                        "/com/puttysoftware/gemma/support/resources/data/faith/"
                                + name
                                + Extension.getFaithExtensionWithPeriod()))) {
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
