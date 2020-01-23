/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericField;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;
import studio.ignitionigloogames.twistedtrek.import1.maze.Maze;

public class Water extends GenericField {
    // Constructors
    public Water() {
	super(new AquaBoots(), true);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
	final Application app = Import1.getApplication();
	if (app.getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
	    this.playMoveSuccessSound();
	}
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
	final Application app = Import1.getApplication();
	Messager.showMessage("You'll drown");
	if (app.getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
	    this.playMoveFailedSound();
	}
    }

    @Override
    public void pushIntoAction(final ObjectInventory inv, final MazeObject pushed, final int x, final int y,
	    final int z, final int w) {
	final Application app = Import1.getApplication();
	if (pushed.isPushable()) {
	    app.getGameManager().morph(new SunkenBlock(), x, y, z, w, Maze.LAYER_GROUND);
	    app.getGameManager().morph(new Empty(), x, y, z, w, Maze.LAYER_OBJECT);
	    if (app.getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
		MazeObject.playSinkBlockSound();
	    }
	}
    }

    @Override
    public String getName() {
	return "Water";
    }

    @Override
    public String getPluralName() {
	return "Squares of Water";
    }

    @Override
    public byte getObjectID() {
	return (byte) 1;
    }

    @Override
    public String getMoveFailedSoundName() {
	return "water";
    }

    @Override
    public String getMoveSuccessSoundName() {
	return "walkwatr";
    }

    @Override
    public boolean overridesDefaultPostMove() {
	return true;
    }

    @Override
    public String getDescription() {
	return "Water is too unstable to walk on without Aqua Boots.";
    }
}
