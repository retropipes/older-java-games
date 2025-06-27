/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.generic;

import studio.ignitionigloogames.twistedtrek.import1.maze.Maze;

public abstract class GenericPassThroughObject extends MazeObject {
    // Constructors
    protected GenericPassThroughObject() {
        super(false);
    }

    protected GenericPassThroughObject(final boolean acceptPushInto, final boolean acceptPushOut,
            final boolean acceptPullInto, final boolean acceptPullOut) {
        super(false, false, acceptPushInto, acceptPushOut, false, acceptPullInto, acceptPullOut, true, false, 0);
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return Maze.LAYER_OBJECT;
    }

    @Override
    public byte getGroupID() {
        return (byte) 3;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_PASS_THROUGH);
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