/*  DynamicDungeon: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.dungeon.abc;

import net.dynamicdungeon.dynamicdungeon.dungeon.DungeonConstants;
import net.dynamicdungeon.dynamicdungeon.dungeon.utilities.TypeConstants;

public abstract class AbstractGround extends AbstractDungeonObject {
    // Constructors
    protected AbstractGround() {
	super(false, true, false);
    }

    protected AbstractGround(final boolean hasFriction) {
	super(false, hasFriction, false);
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
	return DungeonConstants.LAYER_GROUND;
    }

    @Override
    protected void setTypes() {
	this.type.set(TypeConstants.TYPE_GROUND);
    }

    @Override
    public int getCustomProperty(final int propID) {
	return AbstractDungeonObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
	// Do nothing
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY) {
	// Do nothing
    }
}
