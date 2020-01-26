/*  DynamicDungeon: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.dungeon.objects;

import net.dynamicdungeon.dynamicdungeon.DynamicDungeon;
import net.dynamicdungeon.dynamicdungeon.creatures.party.PartyManager;
import net.dynamicdungeon.dynamicdungeon.dungeon.Dungeon;
import net.dynamicdungeon.dynamicdungeon.dungeon.abc.AbstractWall;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.ObjectImageConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.SoundConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.SoundManager;

public class Exit extends AbstractWall {
    // Constructors
    public Exit() {
        super();
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_EXIT;
    }

    // Scriptability
    @Override
    public String getName() {
        return "Exit";
    }

    @Override
    public String getPluralName() {
        return "Exits";
    }

    @Override
    public String getDescription() {
        return "Exits seal the way forward.";
    }

    @Override
    public boolean preMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        final Dungeon m = DynamicDungeon.getApplication().getDungeonManager()
                .getDungeon();
        if (m.getHammers() > 0) {
            m.useHammer();
            SoundManager.playSound(SoundConstants.SOUND_UNLOCK);
            m.setCell(new StairsDown(), dirX, dirY, 0, this.getLayer());
            return true;
        }
        return false;
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        final Dungeon m = DynamicDungeon.getApplication().getDungeonManager()
                .getDungeon();
        if (m.getHammers() == 0) {
            super.postMoveAction(ie, dirX, dirY);
        }
    }

    @Override
    public int getMinimumRequiredQuantity(final Dungeon maze) {
        return PartyManager.getParty().getDungeonLevel() + 1;
    }

    @Override
    public int getMaximumRequiredQuantity(final Dungeon maze) {
        return PartyManager.getParty().getDungeonLevel() + 1;
    }

    @Override
    public boolean isRequired() {
        return true;
    }
}
