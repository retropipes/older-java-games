/*  DungeonDiver4: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.creatures.castes;

import com.puttysoftware.dungeondiver4.descriptionmanagers.CasteDescriptionManager;

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

    static String casteIDtoName(int casteID) {
        return CasteConstants.CASTE_NAMES[casteID];
    }
}
