/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.generic;

import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.maze.Maze;

public abstract class GenericField extends GenericInfiniteLock {
    // Constructors
    protected GenericField(final GenericPass mgp) {
	super(mgp);
    }

    protected GenericField(final GenericPass mgp, final boolean doesAcceptPushInto) {
	super(mgp, doesAcceptPushInto);
    }

    protected GenericField(final boolean isSolid, final GenericPass mgp) {
	super(isSolid, mgp);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
	// Do nothing
    }

    @Override
    public boolean isConditionallySolid(final ObjectInventory inv) {
	return !inv.isItemThere(this.getKey());
    }

    @Override
    public boolean isConditionallyDirectionallySolid(final boolean ie, final int dirX, final int dirY,
	    final ObjectInventory inv) {
	return !inv.isItemThere(this.getKey());
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
	return Maze.LAYER_GROUND;
    }

    @Override
    public byte getGroupID() {
	return (byte) 1;
    }

    @Override
    protected void setTypes() {
	this.type.set(TypeConstants.TYPE_FIELD);
	this.type.set(TypeConstants.TYPE_INFINITE_LOCK);
	this.type.set(TypeConstants.TYPE_LOCK);
    }
}
