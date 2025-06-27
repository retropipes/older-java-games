/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.creatures.castes;

import studio.ignitionigloogames.twistedtrek.import2.descriptionmanagers.CasteDescriptionManager;

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
