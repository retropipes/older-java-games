/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.editor.MazeEditor;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericTeleport;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;

public class OneShotTeleport extends GenericTeleport {
    // Constructors
    public OneShotTeleport() {
	super(0, 0, 0, 0);
    }

    public OneShotTeleport(final int destinationRow, final int destinationColumn, final int destinationFloor,
	    final int destinationLevel) {
	super(destinationRow, destinationColumn, destinationFloor, destinationLevel);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
	final Application app = Import1.getApplication();
	app.getGameManager().decay();
	app.getGameManager().updatePositionAbsolute(this.getDestinationRow(), this.getDestinationColumn(),
		this.getDestinationFloor(), this.getDestinationLevel());
	if (app.getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
	    this.playMoveSuccessSound();
	}
    }

    @Override
    public String getName() {
	return "One-Shot Teleport";
    }

    @Override
    public String getPluralName() {
	return "One-Shot Teleports";
    }

    @Override
    public MazeObject editorPropertiesHook() {
	final MazeEditor me = Import1.getApplication().getEditor();
	final MazeObject mo = me.editTeleportDestination(MazeEditor.TELEPORT_TYPE_ONESHOT);
	return mo;
    }

    @Override
    public byte getObjectID() {
	return (byte) 3;
    }

    @Override
    public String getDescription() {
	return "One-Shot Teleports behave like regular Teleports, except they only work once.";
    }
}