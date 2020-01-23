/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.creatures.genders;

import studio.ignitionigloogames.twistedtrek.import1.resourcemanagers.datamanagers.GenderDataManager;

public class Gender {
    private final int[] data;
    private final int genderID;

    Gender(final int gid) {
	this.data = GenderDataManager.getGenderData(gid);
	this.genderID = gid;
    }

    public int getAttribute(final int aid) {
	return this.data[aid];
    }

    public String getName() {
	return GenderConstants.GENDER_NAMES[this.genderID];
    }

    public int getGenderID() {
	return this.genderID;
    }
}
