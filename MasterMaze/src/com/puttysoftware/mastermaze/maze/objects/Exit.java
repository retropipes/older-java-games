/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.Application;
import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericTeleportTo;
import com.puttysoftware.mastermaze.maze.generic.ObjectInventory;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;

public class Exit extends GenericTeleportTo {
    // Constructors
    public Exit() {
        super(ColorConstants.COLOR_NONE);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_EXIT;
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        final Application app = MasterMaze.getApplication();
        SoundManager.playSound(SoundConstants.SOUND_DOWN);
        app.getGameManager().goToLevel(this.getDestinationLevel());
    }

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
        return "Exits send you outside when walked on.";
    }
}