/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWall;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;
import studio.ignitionigloogames.twistedtrek.import1.maze.Maze;

public class BreakableWallHorizontal extends GenericWall {
    // Constructors
    public BreakableWallHorizontal() {
	super(true, true);
    }

    @Override
    public void chainReactionAction(final int x, final int y, final int z, final int w) {
	if (Import1.getApplication().getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
	    this.playChainReactSound();
	}
	BreakableWallHorizontal.doChainReact(x, y, z, w);
    }

    private static void doChainReact(final int x, final int y, final int z, final int w) {
	final Application app = Import1.getApplication();
	BreakableWallHorizontal curr = null;
	try {
	    curr = (BreakableWallHorizontal) app.getMazeManager().getMazeObject(x, y, z, w, Maze.LAYER_OBJECT);
	} catch (final ClassCastException cce) {
	    // We're not a breakable wall horizontal, so abort
	    return;
	}
	String mo4Name, mo6Name, invalidName, currName;
	invalidName = new EmptyVoid().getName();
	currName = curr.getName();
	final MazeObject mo4 = app.getMazeManager().getMazeObject(x - 1, y, z, w, Maze.LAYER_OBJECT);
	try {
	    mo4Name = mo4.getName();
	} catch (final NullPointerException np) {
	    mo4Name = invalidName;
	}
	final MazeObject mo6 = app.getMazeManager().getMazeObject(x + 1, y, z, w, Maze.LAYER_OBJECT);
	try {
	    mo6Name = mo6.getName();
	} catch (final NullPointerException np) {
	    mo6Name = invalidName;
	}
	app.getGameManager().morph(new Empty(), x, y, z, w);
	if (mo4Name.equals(currName)) {
	    BreakableWallHorizontal.doChainReact(x - 1, y, z, w);
	}
	if (mo6Name.equals(currName)) {
	    BreakableWallHorizontal.doChainReact(x + 1, y, z, w);
	}
    }

    @Override
    public String getName() {
	return "Breakable Wall Horizontal";
    }

    @Override
    public String getGameName() {
	return "Wall";
    }

    @Override
    public String getPluralName() {
	return "Breakable Walls Horizontal";
    }

    @Override
    public byte getObjectID() {
	return (byte) 0;
    }

    @Override
    public String getDescription() {
	return "Breakable Walls Horizontal disintegrate when touched, causing other Breakable Walls Horizontal nearby to also disintegrate.";
    }

    @Override
    public String getChainReactSoundName() {
	return "crack";
    }
}
