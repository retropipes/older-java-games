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

public class Bomb extends AbstractItem {
    // Constructors
    public Bomb() {
        super();
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_BOMB;
    }

    @Override
    public String getName() {
        return "Bomb";
    }

    @Override
    public String getPluralName() {
        return "Bombs";
    }

    @Override
    public String getDescription() {
        return "Bombs let you avoid combat.";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        DynamicDungeon.getApplication().getDungeonManager().getDungeon()
                .addBomb();
        SoundManager.playSound(SoundConstants.SOUND_GRAB);
        GameLogicManager.decay();
    }
}
