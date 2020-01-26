/*  DynamicDungeon: An RPG
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DynamicDungeon@worldwizard.net
 */
package net.dynamicdungeon.dynamicdungeon.dungeon.objects;

import net.dynamicdungeon.dynamicdungeon.DynamicDungeon;
import net.dynamicdungeon.dynamicdungeon.dungeon.abc.AbstractItem;
import net.dynamicdungeon.dynamicdungeon.game.GameLogicManager;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.ObjectImageConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.SoundConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.SoundManager;

public class Tablet extends AbstractItem {
    // Constructors
    public Tablet() {
        super();
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_TABLET;
    }

    @Override
    public String getName() {
        return "Tablet";
    }

    @Override
    public String getPluralName() {
        return "Tablets";
    }

    @Override
    public String getDescription() {
        return "Tablets open Tablet Slots.";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        DynamicDungeon.getApplication().getDungeonManager().getDungeon()
                .addTablet();
        SoundManager.playSound(SoundConstants.SOUND_GRAB);
        GameLogicManager.decay();
    }
}
