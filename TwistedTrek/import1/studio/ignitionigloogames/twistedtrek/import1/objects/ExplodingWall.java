/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWall;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;
import studio.ignitionigloogames.twistedtrek.import1.maze.Maze;

public class ExplodingWall extends GenericWall {
    // Constructors
    public ExplodingWall() {
	super(true, true);
    }

    @Override
    public boolean preMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
	Messager.showMessage("BOOM!");
	return true;
    }

    @Override
    public void chainReactionAction(final int x, final int y, final int z, final int w) {
	// Explode this wall, and any exploding walls next to this wall as well
	final Application app = Import1.getApplication();
	ExplodingWall curr = null;
	try {
	    curr = (ExplodingWall) app.getMazeManager().getMazeObject(x, y, z, w, Maze.LAYER_OBJECT);
	} catch (final ClassCastException cce) {
	    // We're not an exploding wall, so abort
	    return;
	}
	String mo2Name, mo4Name, mo6Name, mo8Name, invalidName, currName;
	invalidName = new EmptyVoid().getName();
	currName = curr.getName();
	final MazeObject mo2 = app.getMazeManager().getMazeObject(x - 1, y, z, w, Maze.LAYER_OBJECT);
	try {
	    mo2Name = mo2.getName();
	} catch (final NullPointerException np) {
	    mo2Name = invalidName;
	}
	final MazeObject mo4 = app.getMazeManager().getMazeObject(x, y - 1, z, w, Maze.LAYER_OBJECT);
	try {
	    mo4Name = mo4.getName();
	} catch (final NullPointerException np) {
	    mo4Name = invalidName;
	}
	final MazeObject mo6 = app.getMazeManager().getMazeObject(x, y + 1, z, w, Maze.LAYER_OBJECT);
	try {
	    mo6Name = mo6.getName();
	} catch (final NullPointerException np) {
	    mo6Name = invalidName;
	}
	final MazeObject mo8 = app.getMazeManager().getMazeObject(x + 1, y, z, w, Maze.LAYER_OBJECT);
	try {
	    mo8Name = mo8.getName();
	} catch (final NullPointerException np) {
	    mo8Name = invalidName;
	}
	app.getGameManager().morph(new Empty(), x, y, z, w, "BOOM!");
	if (mo2Name.equals(currName)) {
	    curr.chainReactionAction(x - 1, y, z, w);
	}
	if (mo4Name.equals(currName)) {
	    curr.chainReactionAction(x, y - 1, z, w);
	}
	if (mo6Name.equals(currName)) {
	    curr.chainReactionAction(x, y + 1, z, w);
	}
	if (mo8Name.equals(currName)) {
	    curr.chainReactionAction(x + 1, y, z, w);
	}
	if (app.getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
	    curr.playChainReactSound();
	}
    }

    @Override
    public String getName() {
	return "Exploding Wall";
    }

    @Override
    public String getGameName() {
	return "Wall";
    }

    @Override
    public String getPluralName() {
	return "Exploding Walls";
    }

    @Override
    public byte getObjectID() {
	return (byte) 2;
    }

    @Override
    public String getDescription() {
	return "Exploding Walls explode when touched, causing other Exploding Walls nearby to also explode.";
    }
}