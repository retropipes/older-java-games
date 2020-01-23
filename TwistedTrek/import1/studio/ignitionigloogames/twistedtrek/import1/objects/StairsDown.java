/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.editor.MazeEditor;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericTeleport;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;

public class StairsDown extends GenericTeleport {
    // Constructors
    public StairsDown() {
	super(0, 0, 0, 0);
    }

    // For derived classes only
    protected StairsDown(final boolean doesAcceptPushInto) {
	super(doesAcceptPushInto);
    }

    @Override
    public String getName() {
	return "Stairs Down";
    }

    @Override
    public String getPluralName() {
	return "Sets of Stairs Down";
    }

    @Override
    public int getDestinationRow() {
	final Application app = Import1.getApplication();
	return app.getGameManager().getPlayerManager().getPlayerLocationX();
    }

    @Override
    public int getDestinationColumn() {
	final Application app = Import1.getApplication();
	return app.getGameManager().getPlayerManager().getPlayerLocationY();
    }

    @Override
    public int getDestinationFloor() {
	final Application app = Import1.getApplication();
	return app.getGameManager().getPlayerManager().getPlayerLocationZ() - 1;
    }

    @Override
    public int getDestinationLevel() {
	final Application app = Import1.getApplication();
	return app.getGameManager().getPlayerManager().getPlayerLocationW();
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
	final Application app = Import1.getApplication();
	app.getGameManager().updatePositionAbsoluteNoEvents(this.getDestinationRow(), this.getDestinationColumn(),
		this.getDestinationFloor(), this.getDestinationLevel());
	if (app.getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
	    this.playMoveSuccessSound();
	}
    }

    @Override
    public void editorPlaceHook() {
	final MazeEditor me = Import1.getApplication().getEditor();
	me.pairStairs(MazeEditor.STAIRS_DOWN);
    }

    @Override
    public MazeObject editorPropertiesHook() {
	return null;
    }

    @Override
    public String getMoveSuccessSoundName() {
	return "down";
    }

    @Override
    public byte getObjectID() {
	return (byte) 6;
    }

    @Override
    public String getDescription() {
	return "Stairs Down lead to the floor below.";
    }

    @Override
    public int getCustomFormat() {
	if (Import1.getApplication().getMazeManager().maze3Compatible()) {
	    // Emulate older format bug
	    return 4;
	} else {
	    return 0;
	}
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
