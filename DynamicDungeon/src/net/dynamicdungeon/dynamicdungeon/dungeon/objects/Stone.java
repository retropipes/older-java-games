/*  DynamicDungeon: An RPG
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DynamicDungeon@worldwizard.net
 */
package net.dynamicdungeon.dynamicdungeon.dungeon.objects;

import net.dynamicdungeon.dynamicdungeon.DynamicDungeon;
import net.dynamicdungeon.dynamicdungeon.dungeon.Dungeon;
import net.dynamicdungeon.dynamicdungeon.dungeon.abc.AbstractItem;
import net.dynamicdungeon.dynamicdungeon.game.GameLogicManager;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.ObjectImageConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.SoundConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.SoundManager;

public class Stone extends AbstractItem {
    // Constructors
    public Stone() {
        super();
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_STONE;
    }

    @Override
    public String getName() {
        return "Stone";
    }

    @Override
    public String getPluralName() {
        return "Stones";
    }

    @Override
    public String getDescription() {
        return "Stones exist to be collected.";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        DynamicDungeon.getApplication().getDungeonManager().getDungeon()
                .addStone();
        SoundManager.playSound(SoundConstants.SOUND_STONE);
        GameLogicManager.decay();
        DynamicDungeon.getApplication().getGameManager().checkStoneCount();
    }

    @Override
    public int getMinimumRequiredQuantity(final Dungeon maze) {
        final int base = maze.getRows();
        final int flux = base / 8;
        return base - flux;
    }

    @Override
    public int getMaximumRequiredQuantity(final Dungeon maze) {
        final int base = maze.getRows();
        final int flux = base / 8;
        return base + flux;
    }

    @Override
    public boolean isRequired() {
        return true;
    }
}
