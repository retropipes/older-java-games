/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericWand;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;
import studio.ignitionigloogames.twistedtrek.import1.maze.Maze;

public class WarpWand extends GenericWand {
    public WarpWand() {
	super();
    }

    @Override
    public String getName() {
	return "Warp Wand";
    }

    @Override
    public String getPluralName() {
	return "Warp Wands";
    }

    @Override
    public void useHelper(final int x, final int y, final int z, final int w) {
	this.useAction(null, x, y, z, w);
	if (Import1.getApplication().getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
	    this.playUseSound();
	}
    }

    @Override
    public void useAction(final MazeObject mo, final int x, final int y, final int z, final int w) {
	final Application app = Import1.getApplication();
	app.getMazeManager().getMaze().warpObject(app.getMazeManager().getMaze().getCell(x, y, z, w, Maze.LAYER_OBJECT),
		x, y, z, w, Maze.LAYER_OBJECT);
    }

    @Override
    public String getUseSoundName() {
	return "teleport";
    }

    @Override
    public byte getObjectID() {
	return (byte) 0;
    }

    @Override
    public String getDescription() {
	return "Warp Wands will teleport the object at the target square to a random location when used.";
    }
}
