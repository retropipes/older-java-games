/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Application;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericMovableObject;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;
import studio.ignitionigloogames.twistedtrek.import1.maze.Maze;

public class PullableBlockTwice extends GenericMovableObject {
    // Constructors
    public PullableBlockTwice() {
        super(false, true);
    }

    @Override
    public String getName() {
        return "Pullable Block Twice";
    }

    @Override
    public String getPluralName() {
        return "Pullable Blocks Twice";
    }

    @Override
    public void pullAction(final ObjectInventory inv, final MazeObject mo, final int x, final int y, final int pushX,
            final int pushY) {
        final Application app = Import1.getApplication();
        app.getGameManager().updatePulledPosition(x, y, pushX, pushY, this);
        if (app.getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            MazeObject.playPullSuccessSound();
        }
        app.getGameManager().morphOther(new PullableBlockOnce(), pushX, pushY, Maze.LAYER_OBJECT);
    }

    @Override
    public byte getObjectID() {
        return (byte) 8;
    }

    @Override
    public String getDescription() {
        return "Pullable Blocks Twice can only be pulled twice, before turning into a wall.";
    }
}