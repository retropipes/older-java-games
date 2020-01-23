/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze.abc;

import studio.ignitionigloogames.twistedtrek.import2.maze.MazeConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.SoundConstants;
import studio.ignitionigloogames.twistedtrek.import2.resourcemanagers.SoundManager;

public abstract class AbstractMarker extends AbstractMazeObject {
    // Constructors
    protected AbstractMarker() {
	super(false, false);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY) {
	SoundManager.playSound(SoundConstants.SOUND_WALK);
    }

    @Override
    public int getLayer() {
	return MazeConstants.VIRTUAL_LAYER_CHARACTER;
    }

    @Override
    protected void setTypes() {
	// Do nothing
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