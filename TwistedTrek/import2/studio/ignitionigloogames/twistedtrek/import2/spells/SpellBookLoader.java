/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.spells;

import studio.ignitionigloogames.twistedtrek.import2.creatures.castes.CasteConstants;
import studio.ignitionigloogames.twistedtrek.import2.creatures.castes.predefined.AnnihilatorSpellBook;
import studio.ignitionigloogames.twistedtrek.import2.creatures.castes.predefined.BufferSpellBook;
import studio.ignitionigloogames.twistedtrek.import2.creatures.castes.predefined.CurerSpellBook;
import studio.ignitionigloogames.twistedtrek.import2.creatures.castes.predefined.DebufferSpellBook;

public class SpellBookLoader {
    // Constructors
    private SpellBookLoader() {
	// Do nothing
    }

    // Methods
    public static SpellBook loadSpellBook(final int sbid) {
	if (sbid == CasteConstants.CASTE_ANNIHILATOR) {
	    return new AnnihilatorSpellBook();
	} else if (sbid == CasteConstants.CASTE_BUFFER) {
	    return new BufferSpellBook();
	} else if (sbid == CasteConstants.CASTE_CURER) {
	    return new CurerSpellBook();
	} else if (sbid == CasteConstants.CASTE_DEBUFFER) {
	    return new DebufferSpellBook();
	} else {
	    // Invalid caste name
	    return null;
	}
    }
}
