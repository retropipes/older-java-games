package net.worldwizard.worldz.creatures.castes;

import net.worldwizard.worldz.resourcemanagers.datamanagers.CasteDataManager;

public class Caste {
    private final int[] data;
    private final int casteID;

    Caste(final int cid) {
        this.data = CasteDataManager.getCasteData(cid);
        this.casteID = cid;
    }

    public int getAttribute(final int aid) {
        return this.data[aid];
    }

    public String getName() {
        return CasteConstants.CASTE_NAMES[this.casteID];
    }

    public int getCasteID() {
        return this.casteID;
    }
}
