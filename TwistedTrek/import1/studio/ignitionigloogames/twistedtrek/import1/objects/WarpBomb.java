/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericUsableObject;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;
import studio.ignitionigloogames.twistedtrek.import1.maze.Maze;

public class WarpBomb extends GenericUsableObject {
    // Constants
    private static final int EFFECT_RADIUS = 3;

    // Constructors
    public WarpBomb() {
	super(1);
    }

    @Override
    public String getName() {
	return "Warp Bomb";
    }

    @Override
    public String getPluralName() {
	return "Warp Bombs";
    }

    @Override
    public byte getObjectID() {
	return (byte) 0;
    }

    @Override
    public String getDescription() {
	return "Warp Bombs randomly teleport anything in an area of radius 3 centered on the target point.";
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY, final int locZ, final int locW, final int dirX,
	    final int dirY, final int arrowType, final ObjectInventory inv) {
	// Destroy bomb
	Import1.getApplication().getGameManager().morph(new Empty(), locX, locY, locZ, locW);
	// Act as if bomb was used
	this.useAction(null, locX, locY, locZ, locW);
	// Stop arrow
	return false;
    }

    @Override
    public void useAction(final MazeObject mo, final int x, final int y, final int z, final int w) {
	// Warp objects
	if (Import1.getApplication().getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
	    this.playUseSound();
	}
	Import1.getApplication().getMazeManager().getMaze().radialScanWarpObjects(x, y, z, w, Maze.LAYER_OBJECT,
		WarpBomb.EFFECT_RADIUS);
	// Player might have moved
	Import1.getApplication().getGameManager().findPlayerAndAdjust();
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