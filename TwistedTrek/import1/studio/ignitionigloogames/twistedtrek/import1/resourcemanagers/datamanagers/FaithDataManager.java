/*  Worldz: A Maze-Solving Game

 */
package studio.ignitionigloogames.twistedtrek.import1.resourcemanagers.datamanagers;

import studio.ignitionigloogames.ioutils.ResourceStreamReader;
import studio.ignitionigloogames.twistedtrek.import1.creatures.faiths.FaithConstants;

public class FaithDataManager {
    public static double[] getFaithData(final int f) {
	final String name = FaithConstants.FAITH_NAMES[f].toLowerCase();
	try (final ResourceStreamReader rsr = new ResourceStreamReader(FaithDataManager.class
		.getResourceAsStream("/net/worldwizard/fantastle5/resources/data/faith/" + name + ".dat"))) {
	    // Fetch data
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
