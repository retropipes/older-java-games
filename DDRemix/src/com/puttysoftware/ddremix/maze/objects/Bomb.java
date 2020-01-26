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
        DDRemix.getApplication().getMazeManager().getMaze().addBomb();
        SoundManager.playSound(SoundConstants.SOUND_GRAB);
        GameLogicManager.decay();
    }
}
