/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.creatures.races;

import studio.ignitionigloogames.twistedtrek.import1.resourcemanagers.datamanagers.RaceDataManager;

public class Race {
    private final int[] data;
    private final int raceID;

    Race(final int rid) {
        this.data = RaceDataManager.getRaceData(rid);
        this.raceID = rid;
    }

    public int getAttribute(final int aid) {
        return this.data[aid];
    }

    public String getName() {
        return RaceConstants.RACE_NAMES[this.raceID];
    }

    public int getRaceID() {
        return this.raceID;
    }
}
