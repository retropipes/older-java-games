/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.generic;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.maze.Maze;

public abstract class GenericToggleWall extends MazeObject {
    // Constructors
    protected GenericToggleWall(final boolean solidState) {
	super(solidState);
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
	final Application app = Import1.getApplication();
	Messager.showMessage("Can't go that way");
	// Play move failed sound, if it's enabled
	if (app.getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
	    this.playMoveFailedSound();
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
	return (byte) 25;
    }

    @Override
    protected void setTypes() {
	this.type.set(TypeConstants.TYPE_TOGGLE_WALL);
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