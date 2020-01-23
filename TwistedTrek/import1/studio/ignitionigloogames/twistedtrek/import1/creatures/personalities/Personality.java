/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.creatures.personalities;

import studio.ignitionigloogames.twistedtrek.import1.resourcemanagers.datamanagers.PersonalityDataManager;

public class Personality {
    private final int[] data;
    private final int personalityID;

    Personality(final int pid) {
	this.data = PersonalityDataManager.getPersonalityData(pid);
	this.personalityID = pid;
    }

    public int getAttribute(final int aid) {
	return this.data[aid];
    }

    public String getName() {
	return PersonalityConstants.PERSONALITY_NAMES[this.personalityID];
    }

    public int getPersonalityID() {
	return this.personalityID;
    }
}
