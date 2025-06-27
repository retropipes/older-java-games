/*  Worldz: A Maze-Solving Game

 */
package studio.ignitionigloogames.twistedtrek.import1.resourcemanagers.datamanagers;

import studio.ignitionigloogames.ioutils.ResourceStreamReader;
import studio.ignitionigloogames.twistedtrek.import1.creatures.personalities.PersonalityConstants;

public class PersonalityDataManager {
	public static int[] getPersonalityData(final int p) {
		final String name = PersonalityConstants.PERSONALITY_NAMES[p].toLowerCase();
		try (final ResourceStreamReader rsr = new ResourceStreamReader(PersonalityDataManager.class
				.getResourceAsStream("/net/worldwizard/fantastle5/resources/data/personality/" + name + ".dat"))) {
			// Fetch data
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