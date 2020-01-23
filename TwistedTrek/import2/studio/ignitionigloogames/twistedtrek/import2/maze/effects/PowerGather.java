/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze.effects;

import studio.ignitionigloogames.twistedtrek.import2.creatures.party.PartyManager;

public class PowerGather extends MazeEffect {
    // Constants
    private static final int MP_GAINED = 3;

    // Constructor
    public PowerGather(final int newRounds) {
	super("Power Gather", newRounds);
    }

    @Override
    public int modifyMove1(final int arg) {
	PartyManager.getParty().getLeader().offsetCurrentMP(PowerGather.MP_GAINED);
	return arg;
    }
}