/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.creatures.monsters;

import studio.ignitionigloogames.twistedtrek.import2.creatures.AbstractCreature;
import studio.ignitionigloogames.twistedtrek.import2.creatures.party.PartyManager;
import studio.ignitionigloogames.twistedtrek.import2.maze.Maze;

public class MonsterFactory {
    private MonsterFactory() {
	// Do nothing
    }

    public static AbstractCreature getNewMonsterInstance() {
	if (PartyManager.getParty().getTowerLevel() == Maze.getMaxLevels() - 1) {
	    return new BossMonster();
	} else {
	    return new BothRandomScalingStaticMonster();
	}
    }
}
