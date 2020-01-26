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
        DDRemix.getApplication().getMazeManager().getMaze().addTablet();
        SoundManager.playSound(SoundConstants.SOUND_GRAB);
        GameLogicManager.decay();
    }
}
