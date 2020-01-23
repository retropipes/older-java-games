/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericUsableObject;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;
import studio.ignitionigloogames.twistedtrek.import1.maze.Maze;

public class SmokeBomb extends GenericUsableObject {
    // Constants
    private static final int EFFECT_RADIUS = 3;
    private static final int STUN_DURATION = 10;

    // Constructors
    public SmokeBomb() {
	super(1);
    }

    @Override
    public String getName() {
	return "Smoke Bomb";
    }

    @Override
    public String getPluralName() {
	return "Smoke Bombs";
    }

    @Override
    public byte getObjectID() {
	return (byte) 0;
    }

    @Override
    public String getDescription() {
	return "Smoke Bombs temporarily stun Monsters in an area of radius 3 centered on the target point.";
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY, final int locZ, final int locW, final int dirX,
	    final int dirY, final int arrowType, final ObjectInventory inv) {
	// Act as if smoke bomb was used
	this.useAction(null, locX, locY, locZ, locW);
	// Destroy smoke bomb
	Import1.getApplication().getGameManager().morph(new Empty(), locX, locY, locZ, locW);
	// Stop arrow
	return false;
    }

    @Override
    public void useAction(final MazeObject mo, final int x, final int y, final int z, final int w) {
	// Paralyze any monsters nearby
	if (Import1.getApplication().getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
	    this.playUseSound();
	}
	Import1.getApplication().getMazeManager().getMaze().radialScanTimerAction(x, y, z, w, Maze.LAYER_OBJECT,
		SmokeBomb.EFFECT_RADIUS, "Monster", SmokeBomb.STUN_DURATION);
    }

    @Override
    public void useHelper(final int x, final int y, final int z, final int w) {
	this.useAction(null, x, y, z, w);
    }

    @Override
    public String getUseSoundName() {
	return "explode";
    }
}