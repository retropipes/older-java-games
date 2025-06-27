/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.generic;

import studio.ignitionigloogames.twistedtrek.import1.maze.Maze;

public abstract class GenericCharacter extends MazeObject {
    // Constructors
    protected GenericCharacter() {
        super(false);
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return Maze.LAYER_OBJECT;
    }

    @Override
    public byte getGroupID() {
        return (byte) 2;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_CHARACTER);
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
