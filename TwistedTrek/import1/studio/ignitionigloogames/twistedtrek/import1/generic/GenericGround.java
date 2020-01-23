/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.generic;

import studio.ignitionigloogames.twistedtrek.import1.maze.Maze;

public abstract class GenericGround extends MazeObject {
    // Constructors
    protected GenericGround() {
	super(false);
    }

    protected GenericGround(final boolean doesAcceptPushInto, final boolean doesAcceptPushOut,
	    final boolean doesAcceptPullInto, final boolean doesAcceptPullOut) {
	super(false, false, doesAcceptPushInto, doesAcceptPushOut, false, doesAcceptPullInto, doesAcceptPullOut, true,
		false, 0, false, false);
    }

    protected GenericGround(final boolean doesAcceptPushInto, final boolean doesAcceptPushOut,
	    final boolean doesAcceptPullInto, final boolean doesAcceptPullOut, final boolean hasFriction) {
	super(false, false, doesAcceptPushInto, doesAcceptPushOut, false, doesAcceptPullInto, doesAcceptPullOut,
		hasFriction, false, 0, false, false);
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
	return Maze.LAYER_GROUND;
    }

    @Override
    public byte getGroupID() {
	return (byte) 0;
    }

    @Override
    protected void setTypes() {
	this.type.set(TypeConstants.TYPE_GROUND);
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