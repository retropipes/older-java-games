/*  TallerTower: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.tallertower.maze.objects;

import com.puttysoftware.tallertower.TallerTower;
import com.puttysoftware.tallertower.game.GameLogicManager;
import com.puttysoftware.tallertower.maze.abc.AbstractTrigger;
import com.puttysoftware.tallertower.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.tallertower.resourcemanagers.SoundConstants;
import com.puttysoftware.tallertower.resourcemanagers.SoundManager;

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
        final GameLogicManager glm = TallerTower.getApplication()
                .getGameManager();
        GameLogicManager.morph(new OpenDoor());
        glm.redrawMaze();
    }
}
