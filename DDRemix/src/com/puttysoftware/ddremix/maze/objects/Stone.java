/*  DDRemix: An RPG
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DDRemix@worldwizard.net
 */
package com.puttysoftware.ddremix.maze.objects;

import com.puttysoftware.ddremix.DDRemix;
import com.puttysoftware.ddremix.game.GameLogicManager;
import com.puttysoftware.ddremix.maze.Maze;
import com.puttysoftware.ddremix.maze.abc.AbstractItem;
import com.puttysoftware.ddremix.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.ddremix.resourcemanagers.SoundConstants;
import com.puttysoftware.ddremix.resourcemanagers.SoundManager;

public class Stone extends AbstractItem {
    // Constructors
    public Stone() {
        super();
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_STONE;
    }

    @Override
    public String getName() {
        return "Stone";
    }

    @Override
    public String getPluralName() {
        return "Stones";
    }

    @Override
    public String getDescription() {
        return "Stones exist to be collected.";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        DDRemix.getApplication().getMazeManager().getMaze().addStone();
        SoundManager.playSound(SoundConstants.SOUND_STONE);
        GameLogicManager.decay();
        DDRemix.getApplication().getGameManager().checkStoneCount();
    }

    @Override
    public int getMinimumRequiredQuantity(final Maze maze) {
        final int base = maze.getRows();
        final int flux = base / 8;
        return base - flux;
    }

    @Override
    public int getMaximumRequiredQuantity(final Maze maze) {
        final int base = maze.getRows();
        final int flux = base / 8;
        return base + flux;
    }

    @Override
    public boolean isRequired() {
        return true;
    }
}
