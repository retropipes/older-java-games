/*  Worldz: A Maze-Solving Game

 */
package studio.ignitionigloogames.twistedtrek.import1.resourcemanagers.datamanagers;

import studio.ignitionigloogames.ioutils.ResourceStreamReader;
import studio.ignitionigloogames.twistedtrek.import1.creatures.races.RaceConstants;

public class RaceDataManager {
    public static int[] getRaceData(final int c) {
	final String name = RaceConstants.RACE_NAMES[c].toLowerCase();
	try (final ResourceStreamReader rsr = new ResourceStreamReader(RaceDataManager.class
		.getResourceAsStream("/net/worldwizard/fantastle5/resources/data/race/" + name + ".dat"))) {
	    // Fetch data
	    final int[] rawData = new int[RaceConstants.RACE_ATTRIBUTE_COUNT];
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
