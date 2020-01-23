/*  DynamicDungeon: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.dungeon.abc;

import net.dynamicdungeon.dynamicdungeon.dungeon.DungeonConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.SoundConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.SoundManager;

public abstract class AbstractMarker extends AbstractDungeonObject {
    // Constructors
    protected AbstractMarker() {
	super(false, false);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY) {
	SoundManager.playSound(SoundConstants.SOUND_WALK);
    }

    @Override
    public int getLayer() {
	return DungeonConstants.VIRTUAL_LAYER_CHARACTER;
    }

    @Override
    protected void setTypes() {
	// Do nothing
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