/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.datamanagers;

import java.io.IOException;

import studio.ignitionigloogames.ioutils.ResourceStreamReader;
import studio.ignitionigloogames.twistedtrek.import2.Import2;
import studio.ignitionigloogames.twistedtrek.import2.creatures.personalities.PersonalityConstants;
import studio.ignitionigloogames.twistedtrek.import2.maze.Extension;

public class PersonalityDataManager {
    public static double[] getPersonalityData(final int p) {
	final String name = PersonalityConstants.getPersonalityName(p).toLowerCase();
	try (final ResourceStreamReader rsr = new ResourceStreamReader(PersonalityDataManager.class
		.getResourceAsStream("/com/puttysoftware/tallertower/resources/data/personality/" + name
			+ Extension.getInternalDataExtensionWithPeriod()))) {
	    // Fetch data
	    final int[] rawData = new int[PersonalityConstants.PERSONALITY_ATTRIBUTES_COUNT];
	    for (int x = 0; x < rawData.length; x++) {
		rawData[x] = rsr.readInt();
	    }
	    // Parse raw data
	    final double[] finalData = new double[rawData.length];
	    for (int x = 0; x < rawData.length; x++) {
		if (x == PersonalityConstants.PERSONALITY_ATTRIBUTE_LEVEL_UP_SPEED) {
		    finalData[x] = PersonalityConstants.getLookupTableEntry(-rawData[x]);
		} else {
		    finalData[x] = PersonalityConstants.getLookupTableEntry(rawData[x]);
		}
	    }
	    return finalData;
	} catch (final IOException e) {
	    Import2.getErrorLogger().logError(e);
	    return null;
	}
    }
}
