/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.creatures.castes;

import studio.ignitionigloogames.twistedtrek.import1.resourcemanagers.datamanagers.CasteDataManager;

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
