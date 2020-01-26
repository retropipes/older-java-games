/*  DDRemix: An RPG
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DDRemix@worldwizard.net
 */
package com.puttysoftware.ddremix.maze.objects;

import com.puttysoftware.ddremix.Application;
import com.puttysoftware.ddremix.DDRemix;
import com.puttysoftware.ddremix.game.GameLogicManager;
import com.puttysoftware.ddremix.maze.Maze;
import com.puttysoftware.ddremix.maze.abc.AbstractItem;
import com.puttysoftware.ddremix.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.ddremix.resourcemanagers.SoundConstants;
import com.puttysoftware.ddremix.resourcemanagers.SoundManager;

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
        final Application app = DDRemix.getApplication();
        app.getMazeManager().getMaze().addHammer();
        SoundManager.playSound(SoundConstants.SOUND_GRAB);
        app.showMessage("The way forward can now be forced open!");
        GameLogicManager.decay();
    }

    @Override
    public int getMinimumRequiredQuantity(final Maze maze) {
        return 1;
    }

    @Override
    public int getMaximumRequiredQuantity(final Maze maze) {
        return 1;
    }

    @Override
    public boolean isRequired() {
        return true;
    }
}
