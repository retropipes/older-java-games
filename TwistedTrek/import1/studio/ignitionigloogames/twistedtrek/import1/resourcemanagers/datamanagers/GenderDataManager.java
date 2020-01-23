/*  Worldz: A Maze-Solving Game

 */
package studio.ignitionigloogames.twistedtrek.import1.resourcemanagers.datamanagers;

import studio.ignitionigloogames.ioutils.ResourceStreamReader;
import studio.ignitionigloogames.twistedtrek.import1.creatures.genders.GenderConstants;

public class GenderDataManager {
    public static int[] getGenderData(final int g) {
	final String name = GenderConstants.GENDER_NAMES[g].toLowerCase();
	try (final ResourceStreamReader rsr = new ResourceStreamReader(GenderDataManager.class
		.getResourceAsStream("/net/worldwizard/fantastle5/resources/data/gender/" + name + ".dat"))) {
	    // Fetch data
	    final int[] rawData = new int[GenderConstants.GENDERS_ATTRIBUTE_COUNT];
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
