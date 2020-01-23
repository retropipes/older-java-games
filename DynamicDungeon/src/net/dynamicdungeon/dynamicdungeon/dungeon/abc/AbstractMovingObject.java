package net.dynamicdungeon.dynamicdungeon.dungeon.abc;

import net.dynamicdungeon.dynamicdungeon.dungeon.DungeonConstants;
import net.dynamicdungeon.dynamicdungeon.dungeon.utilities.TypeConstants;

public abstract class AbstractMovingObject extends AbstractDungeonObject {
    // Constructors
    public AbstractMovingObject(final boolean solid) {
	super(solid, false);
    }

    // Methods
    @Override
    public boolean isMoving() {
	return true;
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY) {
	this.postMoveActionHook();
    }

    public void postMoveActionHook() {
	// Do nothing
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
	return DungeonConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
	this.type.set(TypeConstants.TYPE_DUNGEON);
    }

    @Override
    public int getCustomProperty(final int propID) {
	return AbstractDungeonObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
	// Do nothing
    }
}
