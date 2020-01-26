/*  DDRemix: An RPG
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DDRemix@worldwizard.net
 */
package com.puttysoftware.ddremix.maze.objects;

import com.puttysoftware.ddremix.DDRemix;
import com.puttysoftware.ddremix.game.GameLogicManager;
import com.puttysoftware.ddremix.maze.abc.AbstractItem;
import com.puttysoftware.ddremix.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.ddremix.resourcemanagers.SoundConstants;
import com.puttysoftware.ddremix.resourcemanagers.SoundManager;

public class Key extends AbstractItem {
    // Constructors
    public Key() {
        super();
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_KEY;
    }

    @Override
    public String getName() {
        return "Key";
    }

    @Override
    public String getPluralName() {
        return "Keys";
    }

    @Override
    public String getDescription() {
        return "Keys open Locks.";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        DDRemix.getApplication().getMazeManager().getMaze().addKey();
        SoundManager.playSound(SoundConstants.SOUND_GRAB);
        GameLogicManager.decay();
    }
}
