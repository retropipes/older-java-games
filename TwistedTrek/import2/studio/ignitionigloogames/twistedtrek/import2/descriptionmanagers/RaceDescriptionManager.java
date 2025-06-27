/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.descriptionmanagers;

import java.io.IOException;

import studio.ignitionigloogames.ioutils.ResourceStreamReader;
import studio.ignitionigloogames.twistedtrek.import2.Import2;
import studio.ignitionigloogames.twistedtrek.import2.creatures.races.RaceConstants;
import studio.ignitionigloogames.twistedtrek.import2.maze.Extension;

public class RaceDescriptionManager {
	public static String getRaceDescription(final int r) {
		final String name = RaceConstants.getRaceName(r).toLowerCase();
		try (final ResourceStreamReader rsr = new ResourceStreamReader(RaceDescriptionManager.class
				.getResourceAsStream("/com/puttysoftware/tallertower/resources/descriptions/race/" + name
						+ Extension.getInternalDataExtensionWithPeriod()))) {
			// Fetch description
			final String desc = rsr.readString();
			return desc;
		} catch (final IOException e) {
			Import2.getErrorLogger().logError(e);
			return null;
		}
	}
}
