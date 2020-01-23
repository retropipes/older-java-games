/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.creatures.castes;

import studio.ignitionigloogames.chrystalz.creatures.castes.predefined.Annihilator;
import studio.ignitionigloogames.chrystalz.creatures.castes.predefined.Buffer;
import studio.ignitionigloogames.chrystalz.creatures.castes.predefined.Curer;
import studio.ignitionigloogames.chrystalz.creatures.castes.predefined.Debuffer;

class CasteLoader {
    // Constructors
    private CasteLoader() {
        // Do nothing
    }

    // Methods
    static Caste loadCaste(final String name) {
        if (name.equals(
                CasteConstants.CASTE_NAMES[CasteConstants.CASTE_ANNIHILATOR])) {
            return new Annihilator();
        } else if (name.equals(
                CasteConstants.CASTE_NAMES[CasteConstants.CASTE_BUFFER])) {
            return new Buffer();
        } else if (name.equals(
                CasteConstants.CASTE_NAMES[CasteConstants.CASTE_CURER])) {
            return new Curer();
        } else if (name.equals(
                CasteConstants.CASTE_NAMES[CasteConstants.CASTE_DEBUFFER])) {
            return new Debuffer();
        } else {
            // Invalid caste name
            return null;
        }
    }
}
