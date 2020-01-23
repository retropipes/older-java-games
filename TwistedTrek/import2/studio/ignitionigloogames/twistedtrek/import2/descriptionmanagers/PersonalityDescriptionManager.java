/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.descriptionmanagers;

import java.io.IOException;

import studio.ignitionigloogames.ioutils.ResourceStreamReader;
import studio.ignitionigloogames.twistedtrek.import2.Import2;
import studio.ignitionigloogames.twistedtrek.import2.creatures.personalities.PersonalityConstants;
import studio.ignitionigloogames.twistedtrek.import2.maze.Extension;

public class PersonalityDescriptionManager {
    public static String getPersonalityDescription(final int p) {
	final String name = PersonalityConstants.getPersonalityName(p).toLowerCase();
	try (final ResourceStreamReader rsr = new ResourceStreamReader(PersonalityDescriptionManager.class
		.getResourceAsStream("/com/puttysoftware/tallertower/resources/descriptions/personality/" + name
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
