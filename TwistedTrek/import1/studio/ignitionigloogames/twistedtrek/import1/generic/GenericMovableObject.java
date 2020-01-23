/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.generic;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.maze.Maze;

public abstract class GenericMovableObject extends MazeObject {
    // Constructors
    protected GenericMovableObject(final boolean pushable, final boolean pullable) {
	super(true, pushable, false, false, pullable, false, false, true, false, 0);
    }

    @Override
    public void pushAction(final ObjectInventory inv, final MazeObject mo, final int x, final int y, final int pushX,
	    final int pushY) {
	final Application app = Import1.getApplication();
	app.getGameManager().updatePushedPosition(x, y, pushX, pushY, this);
	if (app.getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
	    MazeObject.playPushSuccessSound();
	}
    }

    @Override
    public void pullAction(final ObjectInventory inv, final MazeObject mo, final int x, final int y, final int pullX,
	    final int pullY) {
	final Application app = Import1.getApplication();
	app.getGameManager().updatePulledPosition(x, y, pullX, pullY, this);
	if (app.getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
	    MazeObject.playPullSuccessSound();
	}
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
	return Maze.LAYER_OBJECT;
    }

    @Override
    public byte getGroupID() {
	return (byte) 21;
    }

    @Override
    protected void setTypes() {
	this.type.set(TypeConstants.TYPE_MOVABLE);
    }

    @Override
    public int getCustomProperty(final int propID) {
	return MazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
	// Do nothing
    }
}