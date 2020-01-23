/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.creatures.castes;

import com.puttysoftware.fantastlex.creatures.castes.predefined.Annihilator;
import com.puttysoftware.fantastlex.creatures.castes.predefined.Buffer;
import com.puttysoftware.fantastlex.creatures.castes.predefined.Curer;
import com.puttysoftware.fantastlex.creatures.castes.predefined.Debuffer;

class CasteLoader {
    // Constructors
    private CasteLoader() {
        // Do nothing
    }

    // Methods
    static Caste loadCaste(final String name) {
        if (name.equals(CasteConstants.CASTE_NAMES[CasteConstants.CASTE_ANNIHILATOR])) {
            return new Annihilator();
        } else if (name
                .equals(CasteConstants.CASTE_NAMES[CasteConstants.CASTE_BUFFER])) {
            return new Buffer();
        } else if (name
                .equals(CasteConstants.CASTE_NAMES[CasteConstants.CASTE_CURER])) {
            return new Curer();
        } else if (name
                .equals(CasteConstants.CASTE_NAMES[CasteConstants.CASTE_DEBUFFER])) {
            return new Debuffer();
        } else {
            // Invalid caste name
            return null;
        }
    }
}
