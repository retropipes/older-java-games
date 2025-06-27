/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.datamanagers;

import java.io.IOException;

import studio.ignitionigloogames.ioutils.ResourceStreamReader;
import studio.ignitionigloogames.twistedtrek.import2.Import2;
import studio.ignitionigloogames.twistedtrek.import2.creatures.races.RaceConstants;
import studio.ignitionigloogames.twistedtrek.import2.maze.Extension;

public class RaceDataManager {
	public static int[] getRaceData(final int r) {
		final String name = RaceConstants.getRaceName(r).toLowerCase();
		try (final ResourceStreamReader rsr = new ResourceStreamReader(
				RaceDataManager.class.getResourceAsStream("/com/puttysoftware/tallertower/resources/data/race/" + name
						+ Extension.getInternalDataExtensionWithPeriod()))) {
			// Fetch data
			final int[] rawData = new int[RaceConstants.RACE_ATTRIBUTE_COUNT];
			for (int x = 0; x < rawData.length; x++) {
				rawData[x] = rsr.readInt();
			}
			return rawData;
		} catch (final IOException e) {
			Import2.getErrorLogger().logError(e);
			return null;
		}
	}
}
