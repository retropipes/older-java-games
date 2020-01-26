/*  DynamicDungeon: An RPG
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DynamicDungeon@worldwizard.net
 */
package net.dynamicdungeon.dynamicdungeon.dungeon.objects;

import net.dynamicdungeon.dynamicdungeon.Application;
import net.dynamicdungeon.dynamicdungeon.DynamicDungeon;
import net.dynamicdungeon.dynamicdungeon.dungeon.Dungeon;
import net.dynamicdungeon.dynamicdungeon.dungeon.abc.AbstractItem;
import net.dynamicdungeon.dynamicdungeon.game.GameLogicManager;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.ObjectImageConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.SoundConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.SoundManager;

public class Hammer extends AbstractItem {
    // Constructors
    public Hammer() {
        super();
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_HAMMER;
    }

    @Override
    public String getName() {
        return "Hammer";
    }

    @Override
    public String getPluralName() {
        return "Hammers";
    }

    @Override
    public String getDescription() {
        return "Hammers let you skip stone collecting.";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        final Application app = DynamicDungeon.getApplication();
        app.getDungeonManager().getDungeon().addHammer();
        SoundManager.playSound(SoundConstants.SOUND_GRAB);
        app.showMessage("The way forward can now be forced open!");
        GameLogicManager.decay();
    }

    @Override
    public int getMinimumRequiredQuantity(final Dungeon maze) {
        return 1;
    }

    @Override
    public int getMaximumRequiredQuantity(final Dungeon maze) {
        return 1;
    }

    @Override
    public boolean isRequired() {
        return true;
    }
}
