/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericMovableObject;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;
import studio.ignitionigloogames.twistedtrek.import1.maze.Maze;

public class PushableBlockOnce extends GenericMovableObject {
    // Constructors
    public PushableBlockOnce() {
	super(true, false);
    }

    @Override
    public String getName() {
	return "Pushable Block Once";
    }

    @Override
    public String getPluralName() {
	return "Pushable Blocks Once";
    }

    @Override
    public void pushAction(final ObjectInventory inv, final MazeObject mo, final int x, final int y, final int pushX,
	    final int pushY) {
	final Application app = Import1.getApplication();
	app.getGameManager().updatePushedPosition(x, y, pushX, pushY, this);
	if (app.getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
	    MazeObject.playPushSuccessSound();
	}
	app.getGameManager().morphOther(new Wall(), pushX, pushY, Maze.LAYER_OBJECT);
    }

    @Override
    public byte getObjectID() {
	return (byte) 4;
    }

    @Override
    public String getDescription() {
	return "Pushable Blocks Once can only be pushed once, before turning into a wall.";
    }
}