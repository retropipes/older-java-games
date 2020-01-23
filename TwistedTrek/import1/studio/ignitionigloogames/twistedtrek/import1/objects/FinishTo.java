/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.editor.MazeEditor;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;

public class FinishTo extends Finish {
    // Constructors
    public FinishTo() {
	super(0);
    }

    public FinishTo(final int newDestinationLevel) {
	super(newDestinationLevel);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
	final Application app = Import1.getApplication();
	if (app.getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
	    this.playMoveSuccessSound();
	}
	if (app.getGameManager().doesLevelExist(this.getDestinationLevel())) {
	    Messager.showDialog("Level Solved!");
	    app.getGameManager().solvedLevelWarp(this.getDestinationLevel());
	} else {
	    Messager.showDialog("Maze Solved!");
	    app.getGameManager().solvedMaze();
	}
    }

    @Override
    public String getName() {
	return "Finish To";
    }

    @Override
    public String getPluralName() {
	return "Finishes To";
    }

    @Override
    public void gameProbeHook() {
	Messager.showMessage("Finish To Level " + (this.getDestinationLevel() + 1));
    }

    @Override
    public void editorProbeHook() {
	Messager.showMessage("Finish To Level " + (this.getDestinationLevel() + 1));
    }

    @Override
    public MazeObject editorPropertiesHook() {
	final MazeEditor me = Import1.getApplication().getEditor();
	final MazeObject mo = me.editFinishToDestination();
	return mo;
    }

    @Override
    public byte getObjectID() {
	return (byte) 2;
    }

    @Override
    public String getDescription() {
	return "Finishes To behave like regular Finishes, except that the level they send you to might not be the next one.";
    }

    @Override
    public int getCustomFormat() {
	return 1;
    }

    @Override
    public int getCustomProperty(final int propID) {
	return this.getDestinationLevel();
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
	this.setDestinationLevel(value);
    }
}