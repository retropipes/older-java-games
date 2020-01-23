/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.creatures.castes;

import studio.ignitionigloogames.chrystalz.manager.asset.CasteDescriptionManager;

public class Caste {
    private final int casteID;
    private final String desc;

    public Caste(final int cid) {
        this.desc = CasteDescriptionManager.getCasteDescription(cid);
        this.casteID = cid;
    }

    public String getDescription() {
        return this.desc;
    }

    public final int getCasteID() {
        return this.casteID;
    }

    static String casteIDtoName(final int casteID) {
        return CasteConstants.CASTE_NAMES[casteID];
    }
}
