/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze.abc;

import studio.ignitionigloogames.twistedtrek.import2.Import2;
import studio.ignitionigloogames.twistedtrek.import2.maze.MazeConstants;
import studio.ignitionigloogames.twistedtrek.import2.maze.utilities.TypeConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.SoundConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.SoundManager;

public abstract class AbstractWall extends AbstractMazeObject {
    // Constructors
    protected AbstractWall() {
	super(true, true);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY) {
	// Do nothing
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX, final int dirY) {
	Import2.getApplication().showMessage("Can't go that way");
	// Play move failed sound, if it's enabled
	SoundManager.playSound(SoundConstants.SOUND_WALK_FAILED);
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
	return MazeConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
	this.type.set(TypeConstants.TYPE_WALL);
    }

    @Override
    public int getCustomProperty(final int propID) {
	return AbstractMazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
	// Do nothing
    }
}