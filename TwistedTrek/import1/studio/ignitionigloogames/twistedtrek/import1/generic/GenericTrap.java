/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.generic;

import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.maze.Maze;

public abstract class GenericTrap extends MazeObject {
    // Constructors
    protected GenericTrap() {
	super(false);
    }

    // Scriptability
    @Override
    public abstract void postMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv);

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
	return Maze.LAYER_OBJECT;
    }

    @Override
    public byte getGroupID() {
	return (byte) 26;
    }

    @Override
    protected void setTypes() {
	this.type.set(TypeConstants.TYPE_TRAP);
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