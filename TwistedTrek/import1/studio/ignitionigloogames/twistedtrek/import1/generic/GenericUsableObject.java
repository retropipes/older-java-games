/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.generic;

import studio.ignitionigloogames.twistedtrek.import1.maze.Maze;

public abstract class GenericUsableObject extends GenericInventoryableObject {
    // Constructors
    protected GenericUsableObject(final int newUses) {
	super(true, newUses);
    }

    @Override
    public abstract void useAction(MazeObject mo, int x, int y, int z, int w);

    @Override
    public abstract String getName();

    @Override
    public byte getGroupID() {
	return (byte) 19;
    }

    @Override
    protected void setTypes() {
	this.type.set(TypeConstants.TYPE_USABLE);
	this.type.set(TypeConstants.TYPE_INVENTORYABLE);
	this.type.set(TypeConstants.TYPE_CONTAINABLE);
    }

    @Override
    public int getLayer() {
	return Maze.LAYER_OBJECT;
    }

    @Override
    public abstract void useHelper(int x, int y, int z, int w);

    @Override
    public int getCustomProperty(final int propID) {
	return MazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
	// Do nothing
    }
}
