/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.descriptionmanagers;

import java.io.IOException;

import studio.ignitionigloogames.ioutils.ResourceStreamReader;
import studio.ignitionigloogames.twistedtrek.import2.Import2;
import studio.ignitionigloogames.twistedtrek.import2.creatures.castes.CasteConstants;
import studio.ignitionigloogames.twistedtrek.import2.maze.Extension;

public class CasteDescriptionManager {
	public static String getCasteDescription(final int c) {
		final String name = CasteConstants.CASTE_NAMES[c].toLowerCase();
		try (final ResourceStreamReader rsr = new ResourceStreamReader(CasteDescriptionManager.class
				.getResourceAsStream("/com/puttysoftware/tallertower/resources/descriptions/caste/" + name
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
