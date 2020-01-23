/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericTeleport;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;

public class Finish extends GenericTeleport {
    // Constructors
    public Finish() {
	super(0, 0, 0, 0);
    }

    public Finish(final int destLevel) {
	super(0, 0, 0, destLevel);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
	final Application app = Import1.getApplication();
	if (app.getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
	    this.playMoveSuccessSound();
	}
	if (app.getGameManager().isLevelAbove()) {
	    Messager.showDialog("Level Solved!");
	    app.getGameManager().solvedLevel();
	} else {
	    Messager.showDialog("Maze Solved!");
	    app.getGameManager().solvedMaze();
	}
    }

    @Override
    public String getName() {
	return "Finish";
    }

    @Override
    public String getPluralName() {
	return "Finishes";
    }

    @Override
    public void editorProbeHook() {
	Messager.showMessage(this.getName());
    }

    @Override
    public MazeObject editorPropertiesHook() {
	return null;
    }

    @Override
    public byte getObjectID() {
	return (byte) 1;
    }

    @Override
    public String getMoveSuccessSoundName() {
	return "finish";
    }

    @Override
    public String getDescription() {
	return "Finishes lead to the next level, if one exists. Otherwise, entering one solves the maze.";
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
}