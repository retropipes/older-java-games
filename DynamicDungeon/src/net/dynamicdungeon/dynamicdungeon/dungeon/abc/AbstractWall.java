/*  DynamicDungeon: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.dungeon.abc;

import net.dynamicdungeon.dynamicdungeon.DynamicDungeon;
import net.dynamicdungeon.dynamicdungeon.dungeon.DungeonConstants;
import net.dynamicdungeon.dynamicdungeon.dungeon.utilities.TypeConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.SoundConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.SoundManager;

public abstract class AbstractWall extends AbstractDungeonObject {
    // Constructors
    protected AbstractWall() {
        super(true, true);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        // Do nothing
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY) {
        DynamicDungeon.getApplication().showMessage("Can't go that way");
        // Play move failed sound, if it's enabled
        SoundManager.playSound(SoundConstants.SOUND_WALK_FAILED);
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return DungeonConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_WALL);
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