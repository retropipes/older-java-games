/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.generic;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.maze.Maze;
import studio.ignitionigloogames.twistedtrek.import1.objects.Empty;
import studio.ignitionigloogames.twistedtrek.import1.resourcemanagers.SoundManager;

public abstract class GenericGem extends MazeObject {
    // Constructors
    protected GenericGem() {
        super(false);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
        Import1.getApplication().getGameManager().decay();
        this.postMoveActionHook();
        Import1.getApplication().getGameManager().redrawMaze();
    }

    public abstract void postMoveActionHook();

    @Override
    public byte getGroupID() {
        return (byte) 29;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_GEM);
        this.type.set(TypeConstants.TYPE_CONTAINABLE);
    }

    @Override
    public int getLayer() {
        return Maze.LAYER_OBJECT;
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY, final int locZ, final int locW, final int dirX,
            final int dirY, final int arrowType, final ObjectInventory inv) {
        Import1.getApplication().getGameManager().morph(new Empty(), locX, locY, locZ, locW);
        if (Import1.getApplication().getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            SoundManager.play("shatter");
        }
        return false;
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
