/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze.effects;

import studio.ignitionigloogames.twistedtrek.import2.creatures.party.PartyManager;

public class PowerWither extends MazeEffect {
    // Constants
    private static final int MP_LOST = -3;

    // Constructor
    public PowerWither(final int newRounds) {
	super("Power Wither", newRounds);
    }

    @Override
    public int modifyMove1(final int arg) {
	PartyManager.getParty().getLeader().offsetCurrentMP(PowerWither.MP_LOST);
	return arg;
    }
}