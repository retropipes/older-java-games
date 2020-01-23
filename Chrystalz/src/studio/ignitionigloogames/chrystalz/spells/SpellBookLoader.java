/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.spells;

import studio.ignitionigloogames.chrystalz.creatures.castes.CasteConstants;
import studio.ignitionigloogames.chrystalz.creatures.castes.predefined.AnnihilatorSpellBook;
import studio.ignitionigloogames.chrystalz.creatures.castes.predefined.BufferSpellBook;
import studio.ignitionigloogames.chrystalz.creatures.castes.predefined.CurerSpellBook;
import studio.ignitionigloogames.chrystalz.creatures.castes.predefined.DebufferSpellBook;

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
