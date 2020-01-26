/*  DynamicDungeon: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.dungeon.objects;

import net.dynamicdungeon.dynamicdungeon.DynamicDungeon;
import net.dynamicdungeon.dynamicdungeon.dungeon.abc.AbstractTrigger;
import net.dynamicdungeon.dynamicdungeon.game.GameLogicManager;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.ObjectImageConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.SoundConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.SoundManager;

public class ClosedDoor extends AbstractTrigger {
    // Constructors
    public ClosedDoor() {
        super();
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_CLOSED_DOOR;
    }

    // Scriptability
    @Override
    public String getName() {
        return "Closed Door";
    }

    @Override
    public String getPluralName() {
        return "Closed Doors";
    }

    @Override
    public String getDescription() {
        return "Closed Doors open when stepped on.";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        SoundManager.playSound(SoundConstants.SOUND_PICK_LOCK);
        final GameLogicManager glm = DynamicDungeon.getApplication()
                .getGameManager();
        GameLogicManager.morph(new OpenDoor());
        glm.redrawDungeon();
    }
}
