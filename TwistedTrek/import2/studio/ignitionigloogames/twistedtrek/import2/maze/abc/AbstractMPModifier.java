/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze.abc;

import studio.ignitionigloogames.twistedtrek.import2.maze.MazeConstants;
import studio.ignitionigloogames.twistedtrek.import2.maze.utilities.TypeConstants;

public abstract class AbstractMPModifier extends AbstractMazeObject {
    // Constructors
    protected AbstractMPModifier() {
	super(false, false);
    }

    @Override
    protected void setTypes() {
	this.type.set(TypeConstants.TYPE_MP_MODIFIER);
    }

    @Override
    public int getLayer() {
	return MazeConstants.LAYER_OBJECT;
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
