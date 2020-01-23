/*  DynamicDungeon: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.dungeon.abc;

import net.dynamicdungeon.dynamicdungeon.dungeon.DungeonConstants;
import net.dynamicdungeon.dynamicdungeon.dungeon.utilities.TypeConstants;

public abstract class AbstractTrigger extends AbstractDungeonObject {
    // Constructors
    protected AbstractTrigger() {
	super(false, true, false);
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
	return DungeonConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
	this.type.set(TypeConstants.TYPE_TRIGGER);
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