/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.creatures.castes;

import studio.ignitionigloogames.twistedtrek.import2.creatures.castes.predefined.Annihilator;
import studio.ignitionigloogames.twistedtrek.import2.creatures.castes.predefined.Buffer;
import studio.ignitionigloogames.twistedtrek.import2.creatures.castes.predefined.Curer;
import studio.ignitionigloogames.twistedtrek.import2.creatures.castes.predefined.Debuffer;

class CasteLoader {
	// Constructors
	private CasteLoader() {
		// Do nothing
	}

	// Methods
	static Caste loadCaste(final String name) {
		if (name.equals(CasteConstants.CASTE_NAMES[CasteConstants.CASTE_ANNIHILATOR])) {
			return new Annihilator();
		} else if (name.equals(CasteConstants.CASTE_NAMES[CasteConstants.CASTE_BUFFER])) {
			return new Buffer();
		} else if (name.equals(CasteConstants.CASTE_NAMES[CasteConstants.CASTE_CURER])) {
			return new Curer();
		} else if (name.equals(CasteConstants.CASTE_NAMES[CasteConstants.CASTE_DEBUFFER])) {
			return new Debuffer();
		} else {
			// Invalid caste name
			return null;
		}
	}
}
