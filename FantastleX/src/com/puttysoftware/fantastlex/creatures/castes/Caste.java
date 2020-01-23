/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.creatures.castes;

import com.puttysoftware.fantastlex.descriptionmanagers.CasteDescriptionManager;

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
