/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.datamanagers;

import java.io.IOException;

import studio.ignitionigloogames.ioutils.ResourceStreamReader;
import studio.ignitionigloogames.twistedtrek.import2.Import2;
import studio.ignitionigloogames.twistedtrek.import2.creatures.faiths.FaithConstants;
import studio.ignitionigloogames.twistedtrek.import2.maze.Extension;

public class FaithDataManager {
    public static double[] getFaithData(final int f) {
	final String name = FaithConstants.getFaithName(f).toLowerCase();
	try (final ResourceStreamReader rsr = new ResourceStreamReader(
		FaithDataManager.class.getResourceAsStream("/com/puttysoftware/tallertower/resources/data/faith/" + name
			+ Extension.getInternalDataExtensionWithPeriod()))) {
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
	} catch (final IOException e) {
	    Import2.getErrorLogger().logError(e);
	    return null;
	}
    }
}
