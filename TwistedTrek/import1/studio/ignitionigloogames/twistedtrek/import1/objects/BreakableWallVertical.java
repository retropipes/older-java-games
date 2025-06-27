/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWall;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;
import studio.ignitionigloogames.twistedtrek.import1.maze.Maze;

public class BreakableWallVertical extends GenericWall {
	// Constructors
	public BreakableWallVertical() {
		super(true, true);
	}

	@Override
	public void chainReactionAction(final int x, final int y, final int z, final int w) {
		if (Import1.getApplication().getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
			this.playChainReactSound();
		}
		this.doChainReact(x, y, z, w);
	}

	public void doChainReact(final int x, final int y, final int z, final int w) {
		final Application app = Import1.getApplication();
		BreakableWallVertical curr = null;
		try {
			curr = (BreakableWallVertical) app.getMazeManager().getMazeObject(x, y, z, w, Maze.LAYER_OBJECT);
		} catch (final ClassCastException cce) {
			// We're not a breakable wall vertical, so abort
			return;
		}
		String mo2Name, mo8Name, invalidName, currName;
		invalidName = new EmptyVoid().getName();
		currName = curr.getName();
		final MazeObject mo2 = app.getMazeManager().getMazeObject(x, y - 1, z, w, Maze.LAYER_OBJECT);
		try {
			mo2Name = mo2.getName();
		} catch (final NullPointerException np) {
			mo2Name = invalidName;
		}
		final MazeObject mo8 = app.getMazeManager().getMazeObject(x, y + 1, z, w, Maze.LAYER_OBJECT);
		try {
			mo8Name = mo8.getName();
		} catch (final NullPointerException np) {
			mo8Name = invalidName;
		}
		app.getGameManager().morph(new Empty(), x, y, z, w);
		if (mo2Name.equals(currName)) {
			curr.doChainReact(x, y - 1, z, w);
		}
		if (mo8Name.equals(currName)) {
			curr.doChainReact(x, y + 1, z, w);
		}
	}

	@Override
	public String getName() {
		return "Breakable Wall Vertical";
	}

	@Override
	public String getGameName() {
		return "Wall";
	}

	@Override
	public String getPluralName() {
		return "Breakable Walls Vertical";
	}

	@Override
	public byte getObjectID() {
		return (byte) 0;
	}

	@Override
	public String getDescription() {
		return "Breakable Walls Vertical disintegrate when touched, causing other Breakable Walls Vertical nearby to also disintegrate.";
	}

	@Override
	public String getChainReactSoundName() {
		return "crack";
	}
}
