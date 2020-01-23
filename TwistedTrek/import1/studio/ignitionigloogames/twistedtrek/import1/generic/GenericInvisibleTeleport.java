/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.generic;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;

public abstract class GenericInvisibleTeleport extends GenericTeleport {
    // Constructors
    protected GenericInvisibleTeleport(final int destinationRow, final int destinationColumn,
	    final int destinationFloor, final int destinationLevel) {
	super(destinationRow, destinationColumn, destinationFloor, destinationLevel);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
	final Application app = Import1.getApplication();
	app.getGameManager().updatePositionAbsolute(this.getDestinationRow(), this.getDestinationColumn(),
		this.getDestinationFloor(), this.getDestinationLevel());
	Messager.showMessage("Invisible Teleport!");
	if (app.getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
	    this.playMoveSuccessSound();
	}
    }

    @Override
    public abstract String getName();

    @Override
    public byte getGroupID() {
	return (byte) 15;
    }

    @Override
    protected void setTypes() {
	this.type.set(TypeConstants.TYPE_INVISIBLE_TELEPORT);
	this.type.set(TypeConstants.TYPE_TELEPORT);
    }

    @Override
    public abstract MazeObject editorPropertiesHook();
}