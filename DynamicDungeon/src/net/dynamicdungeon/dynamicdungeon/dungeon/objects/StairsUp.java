/*  DynamicDungeon: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.dungeon.objects;

import net.dynamicdungeon.dynamicdungeon.Application;
import net.dynamicdungeon.dynamicdungeon.DynamicDungeon;
import net.dynamicdungeon.dynamicdungeon.creatures.party.PartyManager;
import net.dynamicdungeon.dynamicdungeon.dungeon.Dungeon;
import net.dynamicdungeon.dynamicdungeon.dungeon.abc.AbstractDungeonObject;
import net.dynamicdungeon.dynamicdungeon.dungeon.abc.AbstractTeleport;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.ObjectImageConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.SoundConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.SoundManager;

public class StairsUp extends AbstractTeleport {
    // Constructors
    public StairsUp() {
        super(0, 0, 0);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_STAIRS_UP;
    }

    @Override
    public String getName() {
        return "Stairs Up";
    }

    @Override
    public String getPluralName() {
        return "Sets of Stairs Up";
    }

    @Override
    public String getDescription() {
        return "Stairs Up lead to the level above.";
    }

    @Override
    public int getCustomFormat() {
        return 0;
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
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        final Application app = DynamicDungeon.getApplication();
        app.getGameManager().goToLevelOffset(-1);
        SoundManager.playSound(SoundConstants.SOUND_UP);
    }

    @Override
    public int getMinimumRequiredQuantity(final Dungeon maze) {
        if (PartyManager.getParty().getDungeonLevel() == 0) {
            return 0;
        } else {
            return PartyManager.getParty().getDungeonLevel() + 1;
        }
    }

    @Override
    public int getMaximumRequiredQuantity(final Dungeon maze) {
        if (PartyManager.getParty().getDungeonLevel() == 0) {
            return 0;
        } else {
            return PartyManager.getParty().getDungeonLevel() + 1;
        }
    }

    @Override
    public boolean isRequired() {
        if (PartyManager.getParty().getDungeonLevel() == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean shouldGenerateObject(final Dungeon maze, final int row,
            final int col, final int floor, final int level, final int layer) {
        if (PartyManager.getParty().getDungeonLevel() == 0) {
            return false;
        } else {
            return true;
        }
    }
}
